package extensions;

import dataproviders.FirstDataProvider;
import dataproviders.SecondDataProvider;
import org.junit.jupiter.api.extension.AfterAllCallback;
import org.junit.jupiter.api.extension.ExtensionContext;

import static repository.ConcurrentStringSet.getGlobalStorage;

public class AfterAllStatementExtension implements AfterAllCallback {
    @Override
    public void afterAll(ExtensionContext context) throws Exception {
        System.out.println("Заходим в афтер ол");
        System.out.println("\n печатаем первую мапу после всех тестов \n" + FirstDataProvider.firstMap);
        System.out.println("\n печатаем вторую мапу после всех тестов \n" + SecondDataProvider.secondMap);
        System.out.println("***|||***");
        System.out.println("печатаем финальное состояние хранилища " + getGlobalStorage().getContents());
        System.out.println("За время выполнения тестов в хранилище было суммарно добавлено " + getGlobalStorage().getMaxSize());
       getGlobalStorage().printMaxSizeState();
    }
}
