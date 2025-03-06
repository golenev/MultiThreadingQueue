package kotlinExample

import io.qameta.allure.Allure
import org.junit.jupiter.api.parallel.Execution
import org.junit.jupiter.api.parallel.ExecutionMode
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ArgumentsSource
import java.util.function.Supplier

@Execution(ExecutionMode.CONCURRENT)
class MainTestKotlin {

    @FirstDataProviderContextKotlin
    @ParameterizedTest(name = "test group 1 invoke{0}")
    @ArgumentsSource(FirstDataProviderKotlin::class)
    fun test1(officeIdSupplier: Supplier<Long>) {
        Allure.step("Создаём в базе офис ${officeIdSupplier.get()}")
        println("сам тест test1()")
    }

    @FirstDataProviderContextKotlin
    @ParameterizedTest(name = "test group 2 invoke{0}")
    @ArgumentsSource(FirstDataProviderKotlin::class)
    fun test2(officeIdSupplier: Supplier<Long>) {
        Allure.step("Создаём в базе офис ${officeIdSupplier.get()}")
        println("сам тест test2()")
    }
}