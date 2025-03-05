package kotlinExample

import com.github.javafaker.Faker
import jdbc.Queries
import models.Offices
import org.junit.jupiter.api.extension.AfterEachCallback
import org.junit.jupiter.api.extension.ExtensionContext
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.ArgumentsProvider
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.atomic.AtomicInteger
import java.util.concurrent.locks.ReentrantLock
import java.util.function.Supplier
import java.util.stream.Stream
import kotlin.concurrent.withLock


class FirstDataProviderKotlin : ArgumentsProvider, AfterEachCallback {

    companion object {
        val NAMESPACE = ExtensionContext.Namespace.create(FirstDataProviderKotlin::class.java)
        private fun <T> Supplier<T>.memoize(): Supplier<T> = object : Supplier<T> {
            private val value by lazy(this@memoize::get)
            override fun get(): T = value
        }
    }

    private val testDataMap = ConcurrentHashMap<String, ConcurrentHashMap<Int, Supplier<Long>>>()
    private val initialCounter = AtomicInteger(1)
    private val deletionCounter = AtomicInteger(1)

    private fun createLazyRandomOfficeSupplier(origin: Int, bound: Int): Supplier<Long> {
        return Supplier {
            Faker().run {
                Offices(
                    number().numberBetween(40000000L, 900000000L),
                    name().fullName()
                ).also {
                    Queries().insertIntoOfficesWithDelay(it, origin, bound)
                }.officeId
            }
        }.memoize()
    }

    override fun provideArguments(context: ExtensionContext): Stream<out Arguments> {
        val testKey = context.requiredTestMethod.name
        val suppliers = listOf(
            createLazyRandomOfficeSupplier(200, 800),
            createLazyRandomOfficeSupplier(1000, 2000),
            createLazyRandomOfficeSupplier(3500, 6000),
            createLazyRandomOfficeSupplier(8000, 10000)
        )

        // Создаем новую мапу для текущего теста
        val dataMap = ConcurrentHashMap<Int, Supplier<Long>>()

        // Заполняем мапу и создаем аргументы
        val arguments = suppliers.map { supplier ->
            val key = initialCounter.getAndIncrement()
            dataMap[key] = supplier
            Arguments.of(supplier)
        }

        // Сохраняем мапу в контекст
        context.getStore(NAMESPACE).put(testKey, dataMap)

        return arguments.stream()
    }

    override fun afterEach(context: ExtensionContext) {
        val testKey = context.requiredTestMethod.name
        val keyToRemove = deletionCounter.getAndIncrement()

        // Получаем мапу из контекста
        val dataMap = context.getStore(NAMESPACE).get(testKey) as? ConcurrentHashMap<Int, Supplier<Long>>

        dataMap?.let { map ->
            map.remove(keyToRemove)?.get()?.let { officeId ->
                Queries().deleteOfficeById(officeId)

            }
        }
    }
}
