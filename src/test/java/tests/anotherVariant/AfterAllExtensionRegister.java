package tests.anotherVariant;

import org.junit.jupiter.api.extension.AfterAllCallback;
import org.junit.jupiter.api.extension.ExtensionContext;

public class AfterAllExtensionRegister implements AfterAllCallback {
    @Override
    public void afterAll(ExtensionContext context) throws Exception {
        System.out.println(AnotherDataProvider.map + "печатаем мапу после всех тестов");
    }
}
