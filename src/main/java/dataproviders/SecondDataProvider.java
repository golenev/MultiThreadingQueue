package dataproviders;

import com.github.javafaker.Faker;
import org.junit.jupiter.api.extension.AfterEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;

import java.util.HashMap;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Stream;

import static java.util.Objects.requireNonNull;
import static repository.ConcurrentStringSet.getStorage;

public class SecondDataProvider implements ArgumentsProvider, AfterEachCallback {

    public static HashMap<Integer, String> secondMap = new HashMap<>();
    private static AtomicReference<Integer> initialCounter = new AtomicReference<>(1);
    private static AtomicReference<Integer> deletionCounter = new AtomicReference<>(1);

    public String createRandomName() {
        Faker faker = new Faker();
        var createdName = faker.name().fullName();
        getStorage().add(createdName);
        return createdName;
    }

    @Override
    public Stream<? extends Arguments> provideArguments(ExtensionContext extensionContext) {
        List<Arguments> argumentsList = List.of(
                Arguments.of(createRandomName()),
                Arguments.of(createRandomName()),
                Arguments.of(createRandomName()),
                Arguments.of(createRandomName()),
                Arguments.of(createRandomName())
        );
        argumentsList.forEach(arg -> {
            String randomName = (String) arg.get()[0];
            Integer storeKey = initialCounter.getAndSet(initialCounter.get() + 1);
            secondMap.put(storeKey, randomName);
        });
        System.out.printf("second dataprovider: печатаем мапу до теста %s %s%n", extensionContext.getDisplayName(), secondMap);
        return argumentsList.stream();
    }


    @Override
    public void afterEach(ExtensionContext context) {
        System.out.println("second dataprovider: Заходим в афтер ич");
        var currentDeleteKey = deletionCounter.getAndUpdate(a -> a + 1);
        getStorage().remove(secondMap.get(currentDeleteKey));
        requireNonNull(secondMap.remove(currentDeleteKey), "second dataprovider : значение мапы не должно быть null");
    }

}
