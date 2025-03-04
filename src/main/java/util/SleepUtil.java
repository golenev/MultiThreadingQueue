package util;

import io.qameta.allure.Step;

import java.util.Random;

public class SleepUtil {

    @Step("Имитация бизнес логики")
    public static void sleepRandomTime () {
        try {
            Thread.sleep(new Random().nextLong(1000, 10000));
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

}
