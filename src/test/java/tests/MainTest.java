package tests;

import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ArgumentsSource;

@Execution(ExecutionMode.CONCURRENT)
public class MainTest {

    @DataProviderContext
    @ParameterizedTest
    @ArgumentsSource(SomeDataProvider.class)
    void test(String someString) throws InterruptedException {
        Thread.sleep(2000);
    }

    @DataProviderContext
    @ParameterizedTest
    @ArgumentsSource(SomeDataProvider.class)
    void anotherTest(String someString) throws InterruptedException {
        Thread.sleep(2000);
    }


}
