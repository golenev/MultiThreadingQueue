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
import java.util.function.Supplier
import java.util.stream.Stream


class FirstDataProviderKotlin : ArgumentsProvider, AfterEachCallback {

    companion object {
        val NAMESPACE = ExtensionContext.Namespace.create(FirstDataProviderKotlin::class.java)
    }

    private val initialCounter = AtomicInteger(1)
    private val deletionCounter = AtomicInteger(1)

    private fun <T> byCached(block: () -> T): Supplier<T> {
        return object : Supplier<T> {
            private val value by lazy(block)
            override fun get(): T = value
        }
    }

    private fun createOffice(origin: Int, bound: Int): Long {
        return Faker().run {
            Offices(
                number().numberBetween(40000000L, 900000000L),
                name().fullName()
            ).also {
                Queries().insertIntoOfficesWithDelay(it, origin, bound)
            }.officeId
        }
    }

    override fun provideArguments(context: ExtensionContext): Stream<out Arguments> {
        val testKey = context.requiredTestMethod.name
        val suppliers = listOf(
            byCached { createOffice(200, 800) },
            byCached { createOffice(1000, 2000) },
            byCached { createOffice(3500, 6000) },
            byCached { createOffice(8000, 10000) }
        )
        val dataMap = ConcurrentHashMap<Int, Supplier<Long>>()
        val arguments = suppliers.map { supplier ->
            val key = initialCounter.getAndIncrement()
            dataMap[key] = supplier
            Arguments.of(supplier)
        }
        context.getStore(NAMESPACE).put(testKey, dataMap)
        return arguments.stream()
    }

    override fun afterEach(context: ExtensionContext) {
        val testKey = context.requiredTestMethod.name
        val keyToRemove = deletionCounter.getAndIncrement()
        val dataMap = context.getStore(NAMESPACE).get(testKey) as? ConcurrentHashMap<Int, Supplier<Long>>
        dataMap?.let { map ->
            map.remove(keyToRemove)?.get()?.let { officeId ->
                Queries().deleteOfficeById(officeId)
            }
        }
    }
}
