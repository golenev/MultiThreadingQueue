package tests;

import annotations.AfterAllPrintStatement;
import annotations.FirstDataProviderContext;
import annotations.SecondDataProviderContext;
import annotations.ThirdDataProviderContext;
import dataproviders.FirstDataProvider;
import dataproviders.SecondDataProvider;
import dataproviders.ThirdDataProvider;
import io.qameta.allure.Allure;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ArgumentsSource;


@Execution(ExecutionMode.CONCURRENT)
@AfterAllPrintStatement
public class MainTest {

    @FirstDataProviderContext
    @ParameterizedTest(name = "test group 1")
    @ArgumentsSource(FirstDataProvider.class)
    void test1(Long officeId) {
        Allure.step("Создаём в базе офис " + officeId);
        System.out.println("сам тест test1()");
    }

    @FirstDataProviderContext
    @ParameterizedTest(name = "test group 2")
    @ArgumentsSource(FirstDataProvider.class)
    void test2(Long officeId) {
        Allure.step("Создаём в базе офис " + officeId);
        System.out.println("сам тест test2()");
    }

    @SecondDataProviderContext
    @ParameterizedTest(name = "test group 3")
    @ArgumentsSource(SecondDataProvider.class)
    void test3(Long officeId) {
        Allure.step("Создаём в базе офис " + officeId);
        System.out.println("сам тест test3()");
    }

    @SecondDataProviderContext
    @ParameterizedTest(name = "test group 4")
    @ArgumentsSource(SecondDataProvider.class)
    void test4(Long officeId) {
        Allure.step("Создаём в базе офис " + officeId);
        System.out.println("сам тест test4()");
    }

    @FirstDataProviderContext
    @ParameterizedTest(name = "test group 5")
    @ArgumentsSource(FirstDataProvider.class)
    void test5(Long officeId) {
        Allure.step("Создаём в базе офис " + officeId);
        System.out.println("сам тест test5()");
    }

    @FirstDataProviderContext
    @ParameterizedTest(name = "test group 6")
    @ArgumentsSource(FirstDataProvider.class)
    void test6(Long officeId) {
        Allure.step("Создаём в базе офис " + officeId);
        System.out.println("сам тест test6()");
    }

    @ThirdDataProviderContext
    @ParameterizedTest(name = "test group 7")
    @ArgumentsSource(ThirdDataProvider.class)
    void test7(Long officeId) {
        Allure.step("Создаём в базе офис " + officeId);
        System.out.println("сам тест test7()");
    }

    @ThirdDataProviderContext
    @ParameterizedTest(name = "test group 8")
    @ArgumentsSource(ThirdDataProvider.class)
    void test8(Long officeId) {
        Allure.step("Создаём в базе офис " + officeId);
        System.out.println("сам тест test8()");
    }

}
