package util;

import io.qameta.allure.Step;

import java.util.Random;

public class SleepUtil {

    @Step("Имитация бизнес логики")
    public static void sleepRandomTime () {
        try {
            Thread.sleep(new Random().nextLong(1000, 12000));
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public static void sleepRandomTime (int origin, int bound) {
        try {
            Thread.sleep(new Random().nextLong(origin, bound));
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

}
