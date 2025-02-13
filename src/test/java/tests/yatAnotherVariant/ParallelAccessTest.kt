package tests.yatAnotherVariant

import org.junit.jupiter.api.parallel.Execution
import org.junit.jupiter.api.parallel.ExecutionMode
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ArgumentsSource
import java.lang.Thread.sleep

@Execution(ExecutionMode.CONCURRENT)
@YetAnotherContext
class ParallelAccessTest {

    @ParameterizedTest
    @ArgumentsSource(YetAnotherDataProvider::class)
    fun test() {
          sleep(5000)
    }

}