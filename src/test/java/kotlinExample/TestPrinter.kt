package kotlinExample

import org.junit.jupiter.api.parallel.Execution
import org.junit.jupiter.api.parallel.ExecutionMode
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ArgumentsSource

class TestPrinter {

    @NormalActionProviderContext
    @Execution(ExecutionMode.CONCURRENT)
    @ParameterizedTest
    @ArgumentsSource(PrinterKotlinProvider::class)
    fun test1(nameSupplier: () -> String) {
        val name = nameSupplier() // Вызываем лямбду, чтобы получить значение
    }

}