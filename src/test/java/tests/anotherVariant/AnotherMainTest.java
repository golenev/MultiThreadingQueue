package tests.anotherVariant;

import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ArgumentsSource;

@Execution(ExecutionMode.CONCURRENT)
@AfterAllExtension
public class AnotherMainTest {

    @AnotherDataProviderContext
    @ParameterizedTest
    @ArgumentsSource(AnotherDataProvider.class)
    void test2() throws InterruptedException {
        Thread.sleep(5000);
    }

}
