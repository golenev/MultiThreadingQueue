package dataproviders;

import com.github.javafaker.Faker;
import jdbc.Queries;
import models.Office;
import org.junit.jupiter.api.extension.AfterEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;

import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.stream.Stream;

public class FirstDataProvider implements ArgumentsProvider {

    public static final ExtensionContext.Namespace NAMESPACE
            = ExtensionContext.Namespace.create(FirstDataProvider.class);

    public Office createOffice(int origin, int bound) {
        Faker faker = new Faker();
        var officeName = faker.name().fullName();
        var id = faker.number().numberBetween(40000000L, 900000000L);
        var newOffice = new Office(id, officeName);
        new Queries().insertIntoOfficesWithDelay(newOffice, origin, bound);
        return newOffice;
    }

    @Override
    public Stream<? extends Arguments> provideArguments(ExtensionContext context) {
        final ConcurrentLinkedQueue<Office> officeQueue = new ConcurrentLinkedQueue<>();
        String testKey = context.getRequiredTestMethod().getName();
        List<Office> offices = List.of(
                /**
                 * тут мы имитируем поведение тест кейсов,
                 * которые что-то делают на бекенде,
                 * каждый разное время
                 */
                createOffice(200, 800),
                createOffice(1000, 2000),
                createOffice(3500, 6000),
                createOffice(8000, 10000)
        );
        List<Arguments> arguments = offices.stream()
                .map(o -> {
                    officeQueue.add(o);
                    return Arguments.of(o);
                })
                .toList();
        context.getStore(NAMESPACE).put(testKey, officeQueue);
        return arguments.stream();
    }

    /**
     * тут был afterEach который нарушал порядок
     */
//    @Override
//    public void afterEach(ExtensionContext context) {
//        String testKey = context.getRequiredTestMethod().getName();
//        ConcurrentLinkedQueue<Office> officeQueue =
//                context.getStore(NAMESPACE).get(testKey, ConcurrentLinkedQueue.class);
//
//        if (officeQueue != null && !officeQueue.isEmpty()) {
//            Office office = officeQueue.remove();
//            new Queries().deleteOfficeById(office.getOfficeId());
//        } else throw new IllegalStateException("officesQueue is null or empty");
//    }
}