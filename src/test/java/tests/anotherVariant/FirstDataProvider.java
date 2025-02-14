package tests.anotherVariant;

import com.github.javafaker.Faker;
import org.junit.jupiter.api.extension.AfterEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;

import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Stream;

import static java.util.Objects.requireNonNull;
import static org.junit.jupiter.params.provider.Arguments.of;

public class FirstDataProvider implements ArgumentsProvider, AfterEachCallback {

    protected static HashMap<Integer, String> firstMap = new HashMap<>();
    private static AtomicReference<Integer> initialCounter = new AtomicReference<>(1);
    private static AtomicReference<Integer> deletionCounter = new AtomicReference<>(1);

    public String createRandomName() {
        Faker faker = new Faker();
        return faker.name().fullName();
    }

    @Override
    public Stream<? extends Arguments> provideArguments(ExtensionContext extensionContext) {
        List<Arguments> argumentsList = List.of(
                of(createRandomName()),
                of(createRandomName()),
                of(createRandomName()),
                of(createRandomName()),
                of(createRandomName()),
                of(createRandomName()),
                of(createRandomName()),
                of(createRandomName()),
                of(createRandomName()),
                of(createRandomName()),
                of(createRandomName()),
                of(createRandomName()),
                of(createRandomName()),
                of(createRandomName()),
                of(createRandomName()),
                of(createRandomName()),
                of(createRandomName()),
                of(createRandomName()),
                of(createRandomName()),
                of(createRandomName()),
                of(createRandomName()),
                of(createRandomName()),
                of(createRandomName()),
                of(createRandomName()),
                of(createRandomName())
        );
        argumentsList.forEach(arg -> {
            String randomName = (String) arg.get()[0];
            Integer storeKey = initialCounter.getAndSet(initialCounter.get() + 1);
            firstMap.put(storeKey, randomName);
        });
        System.out.printf("first dataprovider : печатаем мапу до теста %s %s%n", extensionContext.getDisplayName(), firstMap);
        return argumentsList.stream();
    }


    @Override
    public void afterEach(ExtensionContext context) throws Exception {
        System.out.println("first dataprovider : Заходим в афтер ич");
        var currentDeleteKey = deletionCounter.getAndUpdate(a -> a + 1);
        requireNonNull(firstMap.remove(currentDeleteKey), "first dataprovider : значение мапы не должно быть null");
    }
}