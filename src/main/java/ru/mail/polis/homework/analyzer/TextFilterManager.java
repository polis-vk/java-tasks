package ru.mail.polis.homework.analyzer;

import java.util.Arrays;
import java.util.HashMap;

/**
 * Задание написать систему фильтрации комментариев.
 * Надо реализовать три типа обязательных фильтров
 * 1) фильтр для слишком длинных текстов (длина задается при создании) (TOO_LONG)
 * 2) фильтр для спама (передается массив плохих слов, которых не должно быть в тексте) (SPAM)
 * 3) фильтр для текстов с плохими эмоциями. (в тексте не должно быть таких смайлов:
 * "=(", ":(", ":|" (NEGATIVE_TEXT)
 * + сделать любой свой фильтр (CUSTOM)
 * <p>
 * Класс TextFilterManager должен содержать все фильтры, которые передаются ему в конструкторе,
 * и при анализе текста через метод analyze должен выдавать первый "успешный" фильтр,
 * если не один не прошел, то возвращать тип GOOD.
 * Дополнительное задание: нужно всем типам фильтров задать приоритет
 * (SPAM, TOO_LONG, NEGATIVE_TEXT, CUSTOM - в таком порядке) и возвращать тип с максимальным приоритетом.
 * Отсортировать фильтра можно с помощью функции
 * Arrays.sort(filter, (filter1, filter2) -> {
 * if (filter1 < filter2) {
 * return -1;
 * } else if (filter1 == filter2) {
 * return 0;
 * }
 * return 1;
 * }
 * где вместо сравнение самих фильтров должно быть стравнение каких-то количественных параметров фильтра
 * <p>
 * 2 балла ( + 2 балла за доп приоритет)
 * Итого 15 баллов + 2 дополнительных
 */
public class TextFilterManager {

    private final TextAnalyzer[] filters;
    private HashMap<FilterType, Integer> mapPriority;

    /**
     * Для работы с каждым элементом массива, нужно использовать цикл for-each
     * Хочется заметить, что тут мы ничего не знаем, какие конкретно нам объекты переданы, знаем только то,
     * что в них реализован интерфейс TextAnalyzer
     */
    public TextFilterManager(TextAnalyzer[] filters) {
        this.filters = filters;
        // приоритет задается через enum, в каком порядке расположены, так и снижается приоритет
        this.mapPriority = setPriority();

        Arrays.sort(this.filters, (filter1, filter2) -> {
            if (mapPriority.get(filter1.getFilterType()) < mapPriority.get(filter2.getFilterType())) {
                return -1;
            } else if (mapPriority.get(filter1.getFilterType()).equals(mapPriority.get(filter2.getFilterType()))) {
                return 0;
            }
            return 1;
        });
    }

    /**
     * Если переменная текст никуда не ссылается, то это означает, что не один фильтр не сработал
     */
    public FilterType analyze(String text) {

        if (text == null || text.equals("")) {
            return FilterType.GOOD;
        }
        FilterType result;
        for (TextAnalyzer filter : filters) {
            result = filter.analyze(text);
            if (result != FilterType.GOOD) {
                return result;
            }
        }
        return FilterType.GOOD;
    }

    private HashMap<FilterType, Integer> setPriority() {
        HashMap<FilterType, Integer> tmpMap = new HashMap<>();
        int value = 1;
        for (FilterType item : FilterType.values()) {
            tmpMap.put(item, value);
            value++;
        }
        return tmpMap;
    }
}
