package tests.anotherVariant;

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
        System.out.println("сам тест");
        Thread.sleep(new Random().nextLong(4000, 7000));
    }

//    @FirstDataProviderContext
//    @ParameterizedTest
//    @ArgumentsSource(FirstDataProvider.class)
//    void test2() throws InterruptedException {
//        Thread.sleep(new Random().nextLong(4000, 7000));
//    }

    @SecondDataProviderContext
    @ParameterizedTest
    @ArgumentsSource(SecondDataProvider.class)
    void test3() throws InterruptedException {
        Thread.sleep(new Random().nextLong(4000, 7000));
    }

//    @SecondDataProviderContext
//    @ParameterizedTest
//    @ArgumentsSource(SecondDataProvider.class)
//    void test4() throws InterruptedException {
//        Thread.sleep(new Random().nextLong(4000, 7000));
//    }

}
