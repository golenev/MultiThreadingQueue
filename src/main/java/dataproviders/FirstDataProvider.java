package dataproviders;

import annotations.FirstDataProviderContext;
import com.github.javafaker.Faker;
import jdbc.Queries;
import models.Offices;
import org.junit.jupiter.api.extension.AfterEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.text.MessageFormat.format;
import static java.util.Objects.requireNonNull;

public class FirstDataProvider implements ArgumentsProvider, AfterEachCallback {

    public static final ExtensionContext.Namespace NAMESPACE
            = ExtensionContext.Namespace.create(FirstDataProviderContext.class);
    private static final ConcurrentHashMap<Integer, Supplier<Long>> dataMap = new ConcurrentHashMap<>();
    private static final AtomicInteger initialCounter = new AtomicInteger(1);
    private static final AtomicInteger deletionCounter = new AtomicInteger(1);

    public Supplier<Long> createLazyRandomOfficeSupplier(int origin, int bound) {
        return new Supplier<>() {
            private Long cachedValue; // Кэшированное значение

            @Override
            public Long get() {
                if (cachedValue == null) {
                    // Вычисляем значение только один раз
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
    public Stream<? extends Arguments> provideArguments(ExtensionContext extensionContext) {
        List<Supplier<Long>> suppliers = List.of(
                createLazyRandomOfficeSupplier(200, 800),
                createLazyRandomOfficeSupplier(1000, 2000),
                createLazyRandomOfficeSupplier(3500, 6000),
                createLazyRandomOfficeSupplier(8000, 10000)
        );
        List<Arguments> argumentsList = suppliers.stream()
                .map(Arguments::of)
                .toList();

        argumentsList.forEach(arg -> {
            Supplier<Long> officeIdSupplier = (Supplier<Long>) arg.get()[0];
            Integer storeKey = initialCounter.getAndIncrement();
            dataMap.put(storeKey, officeIdSupplier);
            var key = extensionContext.getRequiredTestMethod().getName();

            extensionContext.getStore(NAMESPACE).put(key, dataMap);
            System.out.printf("FIRST dataprovider : печатаем мапу до теста %s %s%n", extensionContext.getDisplayName(), dataMap);
        });
        System.out.println(extensionContext.getRequiredTestMethod().getName());
        return argumentsList.stream();
    }

    @Override
    public void afterEach(ExtensionContext context) {
        var key = context.getRequiredTestMethod().getName();
        Integer currentDeleteKey = deletionCounter.getAndIncrement();
        ConcurrentHashMap<Integer, Supplier<Long>> dataMap = (ConcurrentHashMap<Integer, Supplier<Long>>) context.getStore(NAMESPACE).get(key, Map.class);
        Supplier<Long> officeIdSupplier = requireNonNull(dataMap.remove(currentDeleteKey), "FIRST dataprovider: значение мапы не должно быть null");
        Long officeIdValue = officeIdSupplier.get(); // Получаем закэшированное значение
        System.out.println("Удаляем из базы офис " + officeIdValue + " по ключу из хранилища " + currentDeleteKey);
        new Queries().deleteOfficeById(officeIdValue); // Удаляем по закэшированному значению
    }
}