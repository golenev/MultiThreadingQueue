package extensions;

import dataproviders.FirstDataProvider;
import dataproviders.SecondDataProvider;
import dataproviders.ThirdDataProvider;
import org.junit.jupiter.api.extension.AfterAllCallback;
import org.junit.jupiter.api.extension.ExtensionContext;

public class AfterAllStatementExtension implements AfterAllCallback {
    @Override
    public void afterAll(ExtensionContext context) throws Exception {
        System.out.println("Заходим в афтер ол");
        System.out.println("\n печатаем первую мапу после всех тестов \n" + FirstDataProvider.firstMap);
        System.out.println("\n печатаем вторую мапу после всех тестов \n" + SecondDataProvider.secondMap);
        System.out.println("\n печатаем третью мапу после всех тестов \n" + ThirdDataProvider.thirdMap);

        System.out.println("***|||***");
    }
}
