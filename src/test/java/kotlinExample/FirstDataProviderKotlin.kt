package kotlinExample

import com.github.javafaker.Faker
import jdbc.Queries
import models.Office
import org.junit.jupiter.api.extension.AfterEachCallback
import org.junit.jupiter.api.extension.ExtensionContext
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.ArgumentsProvider
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.atomic.AtomicInteger
import java.util.function.Supplier
import java.util.stream.Stream

class FirstDataProviderKotlin : ArgumentsProvider, AfterEachCallback {

    companion object {
        val NAMESPACE = ExtensionContext.Namespace.create(FirstDataProviderKotlin::class.java)
    }

    private val initialCounter = AtomicInteger(1)
    private val deletionCounter = AtomicInteger(1)

    private fun <T> byCached(vararg blocks: () -> T): List<Supplier<T>> {
        return blocks.map { block ->
            object : Supplier<T> {
                private val value by lazy(block)
                override fun get(): T = value
            }
        }
    }

    private fun createOffice(origin: Int, bound: Int): Long {
        return Faker().run {
            Office(
                number().numberBetween(40000000L, 900000000L),
                name().fullName()
            ).also {
                Queries().insertIntoOfficesWithDelay(it, origin, bound)
            }.officeId
        }
    }

    override fun provideArguments(context: ExtensionContext): Stream<out Arguments> {
        val testKey = context.requiredTestMethod.name
        val dataMap = ConcurrentHashMap<Int, Supplier<Long>>()
        val suppliers = byCached(
            //вариант, когда сценарии из аргументов выполняются каждый разное время
//            { createOffice(200, 800) },
//            { createOffice(1000, 2000) },
//            { createOffice(3500, 6000) },
//            { createOffice(8000, 10000) }
            //вариант, когда сценарии из аргументов выполняются одинаковое время
            { createOffice(2000, 2001) },
            { createOffice(2000, 2001) },
            { createOffice(2000, 2001) },
            { createOffice(2000, 2001) },
        )
        val arguments = suppliers.map { supplier ->
            val key = initialCounter.getAndIncrement()
            dataMap[key] = supplier
            Arguments.of(supplier, context)
        }
        context.getStore(NAMESPACE).put(testKey, dataMap)
        return arguments.stream()
    }

    override fun afterEach(context: ExtensionContext) {
        val testKey = context.requiredTestMethod.name
        val keyToRemove = deletionCounter.getAndIncrement()
        val dataMap = context.getStore(NAMESPACE).get(testKey) as? ConcurrentHashMap<Int, Supplier<Long>>
        dataMap?.let { map ->
            map.remove(keyToRemove)?.let { supplier ->
                val officeId = supplier.get() // Вызываем get() на Supplier
                Queries().deleteOfficeById(officeId)
            }
        }
    }
}