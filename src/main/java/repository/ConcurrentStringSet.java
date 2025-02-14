package repository;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentSkipListSet;

public class ConcurrentStringSet {
    // Единственный экземпляр класса
    private static final ConcurrentStringSet INSTANCE = new ConcurrentStringSet();

    // Переменная для хранения максимального размера
    private int maxSize = 0;

    // Список элементов, которые были в хранилище при максимальном размере
    private List<String> maxSizeElements = new ArrayList<>();

    // Потокобезопасное хранилище
    private final ConcurrentSkipListSet<String> set;

    // Приватный конструктор, чтобы запретить создание экземпляров извне
    private ConcurrentStringSet() {
        this.set = new ConcurrentSkipListSet<>();
    }

    /**
     * Возвращает единственный экземпляр хранилища.
     */
    public static ConcurrentStringSet getStorage() {
        return INSTANCE;
    }

    /**
     * Добавляет значение в набор.
     * Если значение уже существует, логирует сообщение и продолжает выполнение.
     */
    public void add(String value) {
        if (!set.add(value)) {
            System.out.println("Value skipped (already exists): " + value);
        } else {
            System.out.println("Value added: " + value);
            // Обновляем максимальный размер и список элементов, если текущий размер больше
            synchronized (this) {
                if (set.size() > maxSize) {
                    maxSize = set.size();
                    maxSizeElements = new ArrayList<>(set); // Сохраняем текущий список элементов
                }
            }
        }
    }

    /**
     * Удаляет значение из набора.
     * Если значение не найдено, логирует сообщение и продолжает выполнение.
     */
    public void remove(String value) {
        if (!set.remove(value)) {
            System.out.println("Value not found (skip removal): " + value);
        } else {
            System.out.println("Value removed: " + value);
        }
    }

    /**
     * Возвращает текущее содержимое хранилища в виде неизменяемого списка.
     */
    public List<String> getContents() {
        return List.copyOf(set);
    }

    /**
     * Возвращает максимальное количество элементов, которое когда-либо было в хранилище.
     */
    public synchronized int getMaxSize() {
        return maxSize;
    }

    /**
     * Печатает в консоль количество и список элементов, которые были в хранилище
     * при максимальном размере.
     */
    public synchronized void printMaxSizeState() {
        System.out.println("Max size ever: " + maxSize);
        System.out.println("Elements at max size: " + maxSizeElements);
    }

    public static void main(String[] args) {

        ConcurrentStringSet set = ConcurrentStringSet.getStorage();

        set.add("Qwerty");
        set.add("Qwerty");
        System.out.println(set.getContents());
        set.remove("Qwerty");
        set.remove("Qwerty");
        System.out.println(set.getContents());

    }

}
