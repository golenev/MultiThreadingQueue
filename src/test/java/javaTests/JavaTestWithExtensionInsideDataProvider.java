package javaTests;

import annotations.DataProviderContext;
import dataproviders.JavaDataProviderWithAfterEachExtension;
import io.qameta.allure.Allure;
import models.Office;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ArgumentsSource;

public class JavaTestWithExtensionInsideDataProvider {

    @DataProviderContext
    @ParameterizedTest(name = "test group 1 is working with {0}")
    @ArgumentsSource(JavaDataProviderWithAfterEachExtension.class)
    void test1(Office office) {
        Allure.step("Создаём в базе офис " + office);
        System.out.println("test1() is executing ...");
    }

    @DataProviderContext
    @ParameterizedTest(name = "test group 2 is working with {0}")
    @ArgumentsSource(JavaDataProviderWithAfterEachExtension.class)
    void test2(Office office) {
        Allure.step("Создаём в базе офис " + office);
        System.out.println("test2() is executing ...");
    }

}
