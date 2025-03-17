package kotlinTests

import dataproviders.KotlinDataProvider
import io.qameta.allure.Allure
import jdbc.Queries
import models.Office
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ArgumentsSource

class KotlinTestWithAfterEachInsideTestClass {

    private lateinit var office: Office

    @ParameterizedTest(name = "test group 1 invoke{0}")
    @ArgumentsSource(KotlinDataProvider::class)
    fun test1(office:Office) {
        this.office = office
        Allure.step("Создаём в базе офис $office")
    }

    @ParameterizedTest(name = "test group 2 invoke{0}")
    @ArgumentsSource(KotlinDataProvider::class)
    fun test2(office:Office) {
        this.office = office
        Allure.step("Создаём в базе офис $office")
    }

    @AfterEach
    fun clearDb() {
        Queries().deleteOfficeById(office.officeId)
    }
}