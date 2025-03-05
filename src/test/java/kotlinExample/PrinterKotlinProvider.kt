package kotlinExample

import com.github.javafaker.Faker
import org.junit.jupiter.api.extension.AfterEachCallback
import org.junit.jupiter.api.extension.ExtensionContext
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.ArgumentsProvider
import util.SleepUtil
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.atomic.AtomicReference
import java.util.stream.Stream

class PrinterKotlinProvider : ArgumentsProvider, AfterEachCallback {

    private companion object {
        private val map: ConcurrentHashMap<Int, () -> String> = ConcurrentHashMap()

        private val initialCounter = AtomicReference(1)
        private val deleteCounter = AtomicReference(1)
    }

    private fun printSomething(origin: Int, bound: Int): String {
        SleepUtil.sleepRandomTime(origin, bound)
        val faker = Faker()
        val name = faker.name().fullName()
        println("Generated: $name")
        return name
    }

    override fun provideArguments(context: ExtensionContext?): Stream<out Arguments> {
        val actions: List<Arguments> = listOf(
            Arguments.of({ printSomething(50, 1000) }),
            Arguments.of({ printSomething(3500, 4000) }),
            Arguments.of({ printSomething(4500, 7000) }),
            Arguments.of({ printSomething(7500, 10000) })
        )
        actions.forEach { arguments ->
            val action = arguments.get()[0] as () -> String
            val storeKey = initialCounter.getAndUpdate { it + 1 }
            map.put(storeKey, action)
            println("положили $action по ключу $storeKey")
        }
        return actions.stream()
    }

    override fun afterEach(context: ExtensionContext?) {
        val currentDeleteKey = deleteCounter.getAndUpdate { it + 1 }
        val action = map.remove(currentDeleteKey)
        val result = action?.invoke() // Вызываем лямбду, чтобы получить строку

        println("имитация обработки $result по ключу $currentDeleteKey")
    }

}