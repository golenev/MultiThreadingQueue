package dataproviders;

import com.github.javafaker.Faker;
import jdbc.Queries;
import models.Offices;
import org.junit.jupiter.api.extension.AfterEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Supplier;
import java.util.stream.Stream;

public class FirstDataProvider implements ArgumentsProvider, AfterEachCallback {

    public static final ExtensionContext.Namespace NAMESPACE
            = ExtensionContext.Namespace.create(FirstDataProvider.class);

    private final ConcurrentHashMap<String, Map<Integer, Supplier<Long>>> testDataMap = new ConcurrentHashMap<>();
    private final AtomicInteger initialCounter = new AtomicInteger(1);
    private final AtomicInteger deletionCounter = new AtomicInteger(1);

    public Supplier<Long> createLazyRandomOfficeSupplier(int origin, int bound) {
        return new Supplier<>() {
            private Long cachedValue; // Кэшированное значение

            @Override
            public Long get() {
                if (cachedValue == null) {
                    Faker faker = new Faker();
                    var officeName = faker.name().fullName();
                    var id = faker.number().numberBetween(40000000L, 900000000L);
                    var newOffice = new Offices(id, officeName);
                    new Queries().insertIntoOfficesWithDelay(newOffice, origin, bound);
                    cachedValue = newOffice.getOfficeId();
                }
                return cachedValue;
            }
        };
    }

    @Override
    public Stream<? extends Arguments> provideArguments(ExtensionContext context) {
        String testKey = context.getRequiredTestMethod().getName();
        List<Supplier<Long>> suppliers = List.of(
                createLazyRandomOfficeSupplier(200, 800),
                createLazyRandomOfficeSupplier(1000, 2000),
                createLazyRandomOfficeSupplier(3500, 6000),
                createLazyRandomOfficeSupplier(8000, 10000)
        );
        Map<Integer, Supplier<Long>> dataMap = testDataMap.computeIfAbsent(
                testKey, k -> new ConcurrentHashMap<>()
        );
        List<Arguments> arguments = suppliers.stream()
                .map(supplier -> {
                    int key = initialCounter.getAndIncrement();
                    dataMap.put(key, supplier);
                    return Arguments.of(supplier);
                })
                .toList();
        context.getStore(NAMESPACE).put(testKey, dataMap);
        return arguments.stream();
    }

    @Override
    public void afterEach(ExtensionContext context) {
        String testKey = context.getRequiredTestMethod().getName();
        int keyToRemove = deletionCounter.getAndIncrement();
        Map<Integer, Supplier<Long>> dataMap = (Map<Integer, Supplier<Long>>)
                context.getStore(NAMESPACE).get(testKey);
        if (dataMap != null) {
            Supplier<Long> supplier = dataMap.remove(keyToRemove);
            if (supplier != null) {
                Long officeId = supplier.get();
                new Queries().deleteOfficeById(officeId);
            }
        }
    }
}