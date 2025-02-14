package tests.anotherVariant;

import jdbc.Queries;
import models.Offices;
import org.junit.jupiter.api.Test;

public class DbExample {

    @Test
    void test () {
   var off = new Offices(1010101010L, "Qwerty");
        new Queries().insertIntoOffices(off);
       // new Queries().deleteOfficeById(off);
    }

}
