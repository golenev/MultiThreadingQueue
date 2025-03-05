package kotlinExample

import org.junit.jupiter.api.extension.ExtendWith


@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
@ExtendWith(PrinterKotlinProvider::class)
annotation class NormalActionProviderContext(
)