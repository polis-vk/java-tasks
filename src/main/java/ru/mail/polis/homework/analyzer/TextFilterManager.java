package ru.mail.polis.homework.analyzer;


import java.util.Arrays;

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
 * <p>
 * 2 балла ( + 2 балла за доп приоритет)
 * Итого 15 баллов + 2 дополнительных
 */
public class TextFilterManager {

    /**
     * Для работы с каждым элементом массива, нужно использовать цикл for-each
     * Хочется заметить, что тут мы ничего не знаем, какие конкретно нам объекты переданы, знаем только то,
     * что в них реализован интерфейс TextAnalyzer
     */

    private final TextAnalyzer[] FILTERS;

    public TextFilterManager(TextAnalyzer[] filters) {
        this.FILTERS = Arrays.copyOf(filters, filters.length);

        Arrays.sort(this.FILTERS, (left, right) -> {
            if (left.getTypeFilter().ordinal() < right.getTypeFilter().ordinal()) {
                return -1;
            } else if (left.getTypeFilter() == right.getTypeFilter()) {
                return 0;
            }
            return 1;
        });

    }

    /**
     * Если переменная текст никуда не ссылается, то это означает, что не один фильтр не сработал
     */
    public FilterType analyze(String text) {
        if (text == null || text.isEmpty()) {
            return FilterType.GOOD;
        }

        for (TextAnalyzer textAnalyzer : FILTERS) {
            if (!textAnalyzer.isCorrect(text)) {
                return textAnalyzer.getTypeFilter();
            }
        }
        return FilterType.GOOD;
    }
}
