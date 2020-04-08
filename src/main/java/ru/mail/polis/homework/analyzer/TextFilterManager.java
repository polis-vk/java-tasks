package ru.mail.polis.homework.analyzer;


import com.sun.tools.classfile.ConstantPool;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

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
 *     if (filter1 < filter2) {
 *         return -1;
 *     } else if (filter1 == filter2) {
 *         return 0;
 *     }
 *     return 1;
 * }
 * где вместо сравнение самих фильтров должно быть стравнение каких-то количественных параметров фильтра
 *
 * 2 балла ( + 2 балла за доп приоритет)
 * Итого 15 баллов + 2 дополнительных
 */
public class TextFilterManager {
    private TextAnalyzer[] filtersInput;
    private final Map<FilterType, Integer> priorityMap= new HashMap<FilterType, Integer>(4) {{
        put(FilterType.SPAM, 4);
        put(FilterType.TOO_LONG, 3);
        put(FilterType.NEGATIVE_TEXT, 2);
        put(FilterType.CUSTOM, 1);
        }};
    /**
     * Для работы с каждым элементом массива, нужно использовать цикл for-each
     * Хочется заметить, что тут мы ничего не знаем, какие конкретно нам объекты переданы, знаем только то,
     * что в них реализован интерфейс TextAnalyzer
     */
    public TextFilterManager(TextAnalyzer[] filters) {
        this.filtersInput = Arrays.copyOf(filters,filters.length);
        Arrays.sort(filtersInput, (filter1, filter2) -> {
            if (priorityMap.get(filter1.getType()) > priorityMap.get(filter2.getType())) {
                return -1;
            } else if (priorityMap.get(filter1.getType()).equals(priorityMap.get(filter2.getType()))) {
                return 0;
            }
            return 1;
        });
    }

    /**
     * Если переменная текст никуда не ссылается, то это означает, что не один фильтр не сработал
     */
    public FilterType analyze(String text) {
        if (text == null || text.length() == 0) {
            return FilterType.GOOD;
        }
        FilterType filterResult = FilterType.GOOD;
        for (TextAnalyzer analyzer : filtersInput) {
            filterResult = analyzer.analyze(text);
            if (filterResult != FilterType.GOOD) {
                return filterResult;
            }
        }
        return filterResult;
    }
}
