package kotlinTests

import com.github.javafaker.Faker
import jdbc.Queries
import models.Office
import org.junit.jupiter.api.extension.ExtensionContext
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.ArgumentsProvider
import java.util.concurrent.ConcurrentLinkedQueue
import java.util.stream.Stream

class FirstDataProviderKotlin : ArgumentsProvider {

    companion object {
        val NAMESPACE = ExtensionContext.Namespace.create(FirstDataProviderKotlin::class.java)
    }

    private fun createOffice(origin: Int, bound: Int): Office {
        return Faker().run {
            Office(
                number().numberBetween(40000000L, 900000000L),
                name().fullName()
            ).also {
                Queries().insertIntoOfficesWithDelay(it, origin, bound)
            }
        }
    }

    override fun provideArguments(context: ExtensionContext): Stream<out Arguments> {
        val testKey = context.requiredTestMethod.name
        val dataQueue = ConcurrentLinkedQueue<Office>()
        val offices = listOf(
            //вариант, когда сценарии из аргументов выполняются одинаковое время
            createOffice(2000, 2001) ,
            createOffice(2000, 2001) ,
             createOffice(2000, 2001) ,
             createOffice(2000, 2001)
        )
        val arguments = offices.map { offices ->
            dataQueue.add(offices)
            Arguments.of(offices)
        }
        context.getStore(NAMESPACE).put(testKey, dataQueue)
        return arguments.stream()
    }

}