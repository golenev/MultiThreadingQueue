package tests.anotherVariant;

import com.github.javafaker.Faker;
import org.junit.jupiter.api.extension.AfterAllCallback;
import org.junit.jupiter.api.extension.AfterEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Stream;

import static java.util.Objects.requireNonNull;
import static org.junit.jupiter.params.provider.Arguments.of;

public class AnotherDataProvider implements ArgumentsProvider, AfterEachCallback {

    protected static Map<Integer, String> map = new HashMap<>();
    private static AtomicReference<Integer> initialCounter = new AtomicReference<>(1);
    private static AtomicReference<Integer> deletionCounter = new AtomicReference<>(1);

    public String createRandomName() {
        Faker faker = new Faker();
        return faker.name().fullName();
    }

    @Override
    public Stream<? extends Arguments> provideArguments(ExtensionContext extensionContext) {
        List<Arguments> argumentsList = Arrays.asList(
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
            map.put(storeKey, randomName);
        });
        System.out.println("печатаем мапу до тестов " + map);
        return argumentsList.stream();
    }


    @Override
    public void afterEach(ExtensionContext context) throws Exception {
        var currentDeleteKey = deletionCounter.getAndUpdate(a -> a + 1);
        requireNonNull(map.remove(currentDeleteKey), "значение мапы не должно быть null");
    }
}