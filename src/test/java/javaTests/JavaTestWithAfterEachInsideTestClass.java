package javaTests;

import dataproviders.JavaDataProvider;
import io.qameta.allure.Allure;
import jdbc.Queries;
import models.Office;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ArgumentsSource;

public class JavaTestWithAfterEachInsideTestClass {

    private Office office;

    @ParameterizedTest(name = "test group 1 is working with {0}")
    @ArgumentsSource(JavaDataProvider.class)
    void test1(Office office) {
        this.office = office;
        Allure.step("Создаём в базе офис " + office);
        System.out.println("test1() is executing ...");
    }

    @ParameterizedTest(name = "test group 2 is working with {0}")
    @ArgumentsSource(JavaDataProvider.class)
    void test2(Office office) {
        this.office = office;
        Allure.step("Создаём в базе офис " + office);
        System.out.println("test2() is executing ...");
    }

    @AfterEach
    void clearDb() {
       new Queries().deleteOfficeById(office.getOfficeId());
    }

}

