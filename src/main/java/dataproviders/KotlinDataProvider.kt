package dataproviders

import jdbc.Queries
import models.Office
import org.junit.jupiter.api.extension.ExtensionContext
import org.junit.jupiter.params.provider.Arguments.of
import org.junit.jupiter.params.provider.ArgumentsProvider
import util.FakeData.getRandomOfficeId
import util.FakeData.getRandomOfficeName

class KotlinDataProvider : ArgumentsProvider {

    private fun createOffice(origin: Int, bound: Int): Office {
        val office =
            Office(
                getRandomOfficeId(),
                getRandomOfficeName()
            )
        Queries().insertIntoOfficesWithDelay(office, origin, bound)
        return office
    }

    override fun provideArguments(context: ExtensionContext) =
        listOf(
            of(createOffice(2000, 2001)),
            of(createOffice(2000, 2001)),
            of(createOffice(2000, 2001)),
            of(createOffice(2000, 2001))
        ).stream()
}