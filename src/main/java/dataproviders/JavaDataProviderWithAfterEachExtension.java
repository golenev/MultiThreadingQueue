package dataproviders;

import annotations.DataProviderContext;
import com.github.javafaker.Faker;
import jdbc.Queries;
import models.Office;
import org.junit.jupiter.api.extension.AfterEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;
import util.FakeData;

import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.stream.Stream;

public class JavaDataProviderWithAfterEachExtension implements ArgumentsProvider, AfterEachCallback {

    private ExtensionContext.Namespace NAMESPACE
            = ExtensionContext.Namespace.create(DataProviderContext.class);

    public Office createOffice(int origin, int bound) {
        var newOffice = new Office(
                FakeData.INSTANCE.getRandomOfficeId(),
                FakeData.INSTANCE.getRandomOfficeName());
        new Queries().insertIntoOfficesWithDelay(newOffice, origin, bound);
        return newOffice;
    }

    @Override
    public Stream<? extends Arguments> provideArguments(ExtensionContext context) {
        final ConcurrentLinkedQueue<Office> officeQueue = new ConcurrentLinkedQueue<>();
        List<Office> offices = List.of(
                /**
                 * тут мы имитируем поведение тест кейсов,
                 * которые что-то делают на бекенде,
                 * аждый возвращает разный officeId,
                 * по которому мы потом удаляем офис в afterEach
                 */
                createOffice(4000, 4001),
                createOffice(4000, 4001),
                createOffice(4000, 4001),
                createOffice(4000, 4001)
        );
        List<Arguments> arguments = offices.stream()
                .map(o -> {
                    officeQueue.add(o);
                    return Arguments.of(o);
                })
                .toList();
        context.getStore(NAMESPACE).put(context.getRequiredTestMethod().getName(), officeQueue);
        return arguments.stream();

    }

    @Override
    public void afterEach(ExtensionContext context) {
        ConcurrentLinkedQueue<Office> officeQueue =
                context.getStore(NAMESPACE).get(context.getRequiredTestMethod().getName(), ConcurrentLinkedQueue.class);
        if (officeQueue != null && !officeQueue.isEmpty()) {
            Office office = officeQueue.remove();
            new Queries().deleteOfficeById(office.getOfficeId());
        } else throw new IllegalStateException("officesQueue is null or empty");
    }
}