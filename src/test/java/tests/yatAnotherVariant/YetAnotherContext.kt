package tests.yatAnotherVariant

import org.junit.jupiter.api.extension.ExtendWith


@Target(AnnotationTarget.FUNCTION, AnnotationTarget.CLASS)
@kotlin.annotation.Retention(AnnotationRetention.RUNTIME)
@ExtendWith(YetAnotherDataProvider::class)
annotation class YetAnotherContext()
