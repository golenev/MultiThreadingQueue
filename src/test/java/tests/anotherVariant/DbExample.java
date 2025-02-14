package tests.anotherVariant;

import jdbc.Queries;
import models.Offices;
import org.junit.jupiter.api.Test;

public class DbExample {

    @Test
    void test () {
        var offices = new Queries().getListOffices();
        System.out.println(offices);
        new Queries().insertIntoOffices(new Offices(1010101010L, "Qwerty"));
        var updOffices = new Queries().getListOffices();
        System.out.println(updOffices);
    }

}
