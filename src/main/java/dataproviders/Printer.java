package dataproviders;

import com.github.javafaker.Faker;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.ParameterContext;
import org.junit.jupiter.api.extension.ParameterResolutionException;
import org.junit.jupiter.api.extension.ParameterResolver;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.aggregator.ArgumentsAccessor;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;
import util.SleepUtil;

import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Stream;

public class Printer implements ArgumentsProvider, ParameterResolver {

    private static final String SUPPLIER_KEY = "supplier";

    private String printSomeThing(int origin, int bound) {
        SleepUtil.sleepRandomTime(origin, bound);
        Faker faker = new Faker();
        String x = faker.name().fullName();
        System.out.println("Generated: " + x);
        return x;
    }

    @Override
    public Stream<? extends Arguments> provideArguments(ExtensionContext extensionContext) throws Exception {
        // Вручную указываем, какие аргументы должны быть сгенерированы
        return Stream.of(
                Arguments.of((Supplier<String>) () -> printSomeThing(50, 1000)),
                Arguments.of((Supplier<String>) () -> printSomeThing(1500, 4000)),
                Arguments.of((Supplier<String>) () -> printSomeThing(4500, 7000)),
                Arguments.of((Supplier<String>) () -> printSomeThing(7500, 10000))
        );
    }

    @Override
    public boolean supportsParameter(ParameterContext parameterContext, ExtensionContext extensionContext) {
        // Поддерживаем только параметры типа String
        return parameterContext.getParameter().getType().equals(String.class);
    }

    @Override
    public Object resolveParameter(ParameterContext parameterContext, ExtensionContext extensionContext) {
        // Извлекаем Supplier из контекста и вызываем его
        return extensionContext.getStore(ExtensionContext.Namespace.create(Printer.class))
                .get(SUPPLIER_KEY, Supplier.class)
                .get();
    }
}