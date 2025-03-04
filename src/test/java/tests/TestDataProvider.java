package tests;

import annotations.FirstDataProviderContext;
import dataproviders.FirstDataProvider;
import dataproviders.Printer;
import io.qameta.allure.Allure;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.aggregator.ArgumentsAccessor;
import org.junit.jupiter.params.provider.ArgumentsSource;
import util.SleepUtil;

import java.util.function.Supplier;

@Execution(ExecutionMode.CONCURRENT)
public class TestDataProvider {

    @ParameterizedTest
    @ArgumentsSource(Printer.class)
    void test1(ArgumentsAccessor arguments) {
        String name = (String) arguments.get(0, Supplier.class).get(); // Извлекаем и вызываем Supplier
    }

}
