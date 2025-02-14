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
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Stream;

import static java.util.Objects.requireNonNull;
import static repository.ConcurrentStringSet.getGlobalStorage;

public class FirstDataProvider implements ArgumentsProvider, AfterEachCallback {

    public static HashMap<Integer, Long> firstMap = new HashMap<>();
    private static AtomicReference<Integer> initialCounter = new AtomicReference<>(1);
    private static AtomicReference<Integer> deletionCounter = new AtomicReference<>(1);

    public Long createRandomOffice() {
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
                Arguments.of(createRandomOffice()),
                Arguments.of(createRandomOffice()),
                Arguments.of(createRandomOffice()),
                Arguments.of(createRandomOffice()),
                Arguments.of(createRandomOffice()),
                Arguments.of(createRandomOffice()),
                Arguments.of(createRandomOffice()),
                Arguments.of(createRandomOffice()),
                Arguments.of(createRandomOffice()),
                Arguments.of(createRandomOffice()),
                Arguments.of(createRandomOffice()),
                Arguments.of(createRandomOffice()),
                Arguments.of(createRandomOffice()),
                Arguments.of(createRandomOffice()),
                Arguments.of(createRandomOffice()),
                Arguments.of(createRandomOffice()),
                Arguments.of(createRandomOffice()),
                Arguments.of(createRandomOffice()),
                Arguments.of(createRandomOffice()),
                Arguments.of(createRandomOffice()),
                Arguments.of(createRandomOffice()),
                Arguments.of(createRandomOffice()),
                Arguments.of(createRandomOffice()),
                Arguments.of(createRandomOffice()),
                Arguments.of(createRandomOffice()),
                Arguments.of(createRandomOffice()),
                Arguments.of(createRandomOffice()),
                Arguments.of(createRandomOffice()),
                Arguments.of(createRandomOffice()),
                Arguments.of(createRandomOffice()),
                Arguments.of(createRandomOffice()),
                Arguments.of(createRandomOffice()),
                Arguments.of(createRandomOffice()),
                Arguments.of(createRandomOffice())
        );
        argumentsList.forEach(arg -> {
            Long officeId = (Long) arg.get()[0];
            Integer storeKey = initialCounter.getAndSet(initialCounter.get() + 1);
            firstMap.put(storeKey, officeId);
           /* getGlobalStorage().add(officeId);*/
        });
        System.out.printf("first dataprovider : печатаем мапу до теста %s %s%n", extensionContext.getDisplayName(), firstMap);
        return argumentsList.stream();
    }


    @Override
    public void afterEach(ExtensionContext context)  {
        System.out.println("first dataprovider : Заходим в афтер ич");
        Integer currentDeleteKey = deletionCounter.getAndUpdate(a -> a + 1);
     //   getGlobalStorage().remove(firstMap.get(currentDeleteKey));
        Long officeIdValue =  requireNonNull(firstMap.remove(currentDeleteKey), "first dataprovider : значение мапы не должно быть null");
        new Queries().deleteOfficeById(officeIdValue);
    }
}