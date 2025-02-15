package tests.anotherVariant;

import annotations.AfterAllPrintStatement;
import annotations.FirstDataProviderContext;
import annotations.SecondDataProviderContext;
import annotations.ThirdDataProviderContext;
import dataproviders.FirstDataProvider;
import dataproviders.SecondDataProvider;
import dataproviders.ThirdDataProvider;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ArgumentsSource;

import java.util.Random;

@Execution(ExecutionMode.CONCURRENT)
@AfterAllPrintStatement
public class MainTest {

    @FirstDataProviderContext
    @ParameterizedTest
    @ArgumentsSource(FirstDataProvider.class)
    void test1() throws InterruptedException {
        System.out.println("сам тест test1()");
        Thread.sleep(new Random().nextLong(7000, 11000));
    }

    @FirstDataProviderContext
    @ParameterizedTest
    @ArgumentsSource(FirstDataProvider.class)
    void test2() throws InterruptedException {
        System.out.println("сам тест test2()");
        Thread.sleep(new Random().nextLong(7000, 11000));
    }

    @SecondDataProviderContext
    @ParameterizedTest
    @ArgumentsSource(SecondDataProvider.class)
    void test3() throws InterruptedException {
        System.out.println("сам тест test3()");
        Thread.sleep(new Random().nextLong(7000, 11000));
    }

    @SecondDataProviderContext
    @ParameterizedTest
    @ArgumentsSource(SecondDataProvider.class)
    void test4() throws InterruptedException {
        System.out.println("сам тест test4()");
        Thread.sleep(new Random().nextLong(7000, 11000));
    }

    @FirstDataProviderContext
    @ParameterizedTest
    @ArgumentsSource(FirstDataProvider.class)
    void test5() throws InterruptedException {
        System.out.println("сам тест test5()");
        Thread.sleep(new Random().nextLong(7000, 11000));
    }

    @FirstDataProviderContext
    @ParameterizedTest
    @ArgumentsSource(FirstDataProvider.class)
    void test6() throws InterruptedException {
        System.out.println("сам тест test6()");
        Thread.sleep(new Random().nextLong(7000, 11000));
    }

    @ThirdDataProviderContext
    @ParameterizedTest
    @ArgumentsSource(ThirdDataProvider.class)
    void test7() throws InterruptedException {
        System.out.println("сам тест test7()");
        Thread.sleep(new Random().nextLong(7000, 11000));
    }

    @ThirdDataProviderContext
    @ParameterizedTest
    @ArgumentsSource(ThirdDataProvider.class)
    void test8() throws InterruptedException {
        System.out.println("сам тест test8()");
        Thread.sleep(new Random().nextLong(7000, 11000));
    }

}
