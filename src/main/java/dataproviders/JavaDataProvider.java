package dataproviders;

import com.github.javafaker.Faker;
import jdbc.Queries;
import models.Office;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;

import java.util.List;
import java.util.stream.Stream;

public class JavaDataProvider implements ArgumentsProvider {

    public Office createOffice(int origin, int bound) {
        Faker faker = new Faker();
        var officeName = faker.name().fullName();
        var id = faker.number().numberBetween(40000000L, 900000000L);
        var newOffice = new Office(id, officeName);
        new Queries().insertIntoOfficesWithDelay(newOffice, origin, bound);
        return newOffice;
    }

    @Override
    public Stream<? extends Arguments> provideArguments(ExtensionContext context) {
        List<Arguments> arguments = List.of(
                /**
                 * тут мы имитируем поведение тест кейсов,
                 * которые что-то делают на бекенде,
                 * каждый разное время
                 */
                Arguments.of(createOffice(4000, 4001)),
                Arguments.of(createOffice(4000, 4001)),
                Arguments.of(createOffice(4000, 4001)),
                Arguments.of(createOffice(4000, 4001))
        );
        return arguments.stream();
    }

}