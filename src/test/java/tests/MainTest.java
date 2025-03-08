package tests;

import annotations.FirstDataProviderContext;
import dataproviders.FirstDataProvider;
import io.qameta.allure.Allure;
import models.Office;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ArgumentsSource;

@Execution(ExecutionMode.CONCURRENT)
public class MainTest {

    @FirstDataProviderContext
    @ParameterizedTest(name = "test group 1 is working with {0}")
    @ArgumentsSource(FirstDataProvider.class)
    void test1(Office office) {
        Allure.step("Создаём в базе офис " + office);
        System.out.println("test1() is executing ...");
    }

    @FirstDataProviderContext
    @ParameterizedTest(name = "test group 2 is working with {0}")
    @ArgumentsSource(FirstDataProvider.class)
    void test2(Office office) {
        Allure.step("Создаём в базе офис " + office);
        System.out.println("test2() is executing ...");
    }


}
