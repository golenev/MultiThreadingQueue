package kotlinExample

import org.junit.jupiter.api.extension.ExtendWith

@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
@ExtendWith(FirstDataProviderKotlin::class)
annotation class FirstDataProviderContextKotlin
