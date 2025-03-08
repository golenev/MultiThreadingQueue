package kotlinExample

import io.qameta.allure.Allure
import org.junit.jupiter.api.extension.ExtensionContext
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ArgumentsSource
import java.util.function.Supplier

class MainTestKotlin {

    @FirstDataProviderContextKotlin
    @ParameterizedTest(name = "test group 1 invoke{0}")
    @ArgumentsSource(FirstDataProviderKotlin::class)
    fun test1(officeIdSupplier: Supplier<Long>, context: ExtensionContext) {
        Allure.step("Создаём в базе офис $officeIdSupplier")
        println("сам тест test1()")
    }

    /**
     *        // Получаем человекочитаемое имя теста из контекста
     *         val testName = officeIdSupplier.get().toString()
     *         // Получаем AllureLifecycle
     *         val allureLifecycle = Allure.getLifecycle()
     *         // Обновляем имя теста в Allure
     *         allureLifecycle.updateTestCase { testResult: TestResult ->
     *             testResult.name = testName
     *         }
     *         // Добавляем шаг в Allure
     *         Allure.step("Создаём в базе офис $officeIdSupplier")
     */

    @FirstDataProviderContextKotlin
    @ParameterizedTest(name = "test group 2 invoke{0}")
    @ArgumentsSource(FirstDataProviderKotlin::class)
    fun test2(officeIdSupplier: Supplier<Long>) {
        Allure.step("Создаём в базе офис $officeIdSupplier")
        println("сам тест test2()")
    }
}