package tests.yatAnotherVariant

import org.junit.jupiter.api.extension.AfterAllCallback
import org.junit.jupiter.api.extension.AfterEachCallback
import org.junit.jupiter.api.extension.ExtensionContext
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.Arguments.of
import org.junit.jupiter.params.provider.ArgumentsProvider
import java.util.*
import java.util.concurrent.ConcurrentHashMap
import java.util.stream.Stream

class YetAnotherDataProvider : ArgumentsProvider, AfterEachCallback, AfterAllCallback {

    companion object {
        val map: ConcurrentHashMap<Int, UUID> = ConcurrentHashMap()
        // Создаем Map для хранения объектов-заглушек для синхронизации
    }

    override fun provideArguments(p0: ExtensionContext?): Stream<out Arguments> {
        val actions: List<Arguments> = listOf(
            of(map.put(1, UUID.randomUUID())),
            of(map.put(2, UUID.randomUUID())),
            of(map.put(3, UUID.randomUUID())),
            of(map.put(4, UUID.randomUUID())),
            of(map.put(5, UUID.randomUUID())),

        )
        return actions.stream()
    }

    override fun afterEach(context: ExtensionContext?) {
        println("Поочередно удаляем каждый ключ потокобезопасно")
        map.keys.forEach { key ->
            map.remove(key)
            println("Key $key removed from map and database")
        }
    }

    override fun afterAll(context: ExtensionContext?) {
        println("Map size after all tests: ${map.size}")
    }
}