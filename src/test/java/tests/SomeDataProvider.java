package tests;

import com.github.javafaker.Faker;
import org.junit.jupiter.api.extension.AfterEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;

import java.util.*;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Stream;

import static java.util.Objects.*;
import static org.junit.jupiter.params.provider.Arguments.of;

public class SomeDataProvider implements ArgumentsProvider, AfterEachCallback {

    private final ExtensionContext.Namespace NAMESPACE
            = ExtensionContext.Namespace.create(DataProviderContext.class);

    public String createRandomName() {
        Faker faker = new Faker();
        return faker.name().fullName();
    }

    @Override
    public Stream<? extends Arguments> provideArguments(ExtensionContext extensionContext) {
        Queue<Integer> keysQueue = new ConcurrentLinkedQueue<>();
        // Queue<Integer> keysQueue = new ArrayDeque<>();
        AtomicReference<Integer> counter = new AtomicReference<>(1);
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
            Integer storeKey = counter.getAndSet(counter.get() + 1);
            keysQueue.add(storeKey);
            extensionContext.getStore(NAMESPACE).put(storeKey, randomName);
            extensionContext.getStore(NAMESPACE).put("qeueu", keysQueue);
            System.out.println("Положили в стору имя " + randomName + " по ключу " + storeKey);
        });
        return argumentsList.stream();
    }

    @Override
    public void afterEach(ExtensionContext extensionContext) {
        Queue<Integer> keysQueue = extensionContext.getStore(NAMESPACE).get("qeueu", Queue.class);
        synchronized (keysQueue) {
            Integer storeKey = requireNonNull(keysQueue.poll(), "ключ  из очереди null ");
            String puttedName = extensionContext.getStore(NAMESPACE).get(storeKey, String.class);
            System.out.println("Достали из сторы положенное ранее имя = " + puttedName + " по ключу " + storeKey);
        }
        System.out.println(keysQueue + " печатает остатки очереди после каждого теста");
    }
}