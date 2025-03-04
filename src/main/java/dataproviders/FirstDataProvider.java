package dataproviders;

import com.github.javafaker.Faker;
import jdbc.Queries;
import models.Offices;
import org.junit.jupiter.api.extension.AfterEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;

import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Stream;

import static java.text.MessageFormat.format;
import static java.util.Objects.requireNonNull;

public class FirstDataProvider implements ArgumentsProvider, AfterEachCallback {

    public static ConcurrentHashMap<Integer, Long> dataMap = new ConcurrentHashMap<>();
    private static AtomicReference<Integer> initialCounter = new AtomicReference<>(1);
    private static AtomicReference<Integer> deletionCounter = new AtomicReference<>(1);

    private Long createRandomOffice() {
        Faker faker = new Faker();
        var officeName = faker.name().fullName();
        var id = faker.number().numberBetween(40000000L, 900000000L);
        var newOffice = new Offices(id, officeName);
        new Queries().insertIntoOffices(newOffice);
        return newOffice.getOfficeId();
    }

    @Override
    public Stream<? extends Arguments> provideArguments(ExtensionContext extensionContext) {
        List<Arguments> argumentsList = List.of(
                Arguments.of(createRandomOffice()),
                Arguments.of(createRandomOffice()),
                Arguments.of(createRandomOffice()),
                Arguments.of(createRandomOffice())
        );
        argumentsList.forEach(arg -> {
            Long officeId = (Long) arg.get()[0];
            Integer storeKey = initialCounter.getAndSet(initialCounter.get() + 1);
            dataMap.put(storeKey, officeId);
        });
        System.out.printf("FIRST dataprovider : печатаем мапу до теста %s %s%n", extensionContext.getDisplayName(), dataMap);
        return argumentsList.stream();
    }

    @Override
    public void afterEach(ExtensionContext context) {
        Integer currentDeleteKey = deletionCounter.getAndUpdate(a -> a + 1);
        Long officeIdValue = requireNonNull(dataMap.remove(currentDeleteKey), "FIRST dataprovider : значение мапы не должно быть null");
        System.out.println("Удаляем из базы офис " + officeIdValue + " по ключу из хранилища " + currentDeleteKey);
        new Queries().deleteOfficeById(officeIdValue);
    }
}