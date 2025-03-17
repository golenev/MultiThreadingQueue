package util

import com.github.javafaker.Faker

object FakeData {
    private val faker: Faker = Faker()

    fun getRandomOfficeId() = faker.number().numberBetween(40000000L, 900000000L)
    fun getRandomOfficeName() =  faker.name().fullName()
}