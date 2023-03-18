package ru.mail.polis.homework.analyzer;


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
 * если ни один не прошел, то возвращать тип GOOD.
 * <p>
 * Усложненное задание на полный балл: нужно всем типам фильтров задать приоритет
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
 * где вместо сравнения самих фильтров должно быть сравнение каких-то количественных параметров фильтра
 * <p>
 * 2 тугрика за класс
 * 5 тугриков за приоритет
 * Итого 20 тугриков за все задание
 */
public class TextFilterManager {
    FilterNegative filterNegative;
    FilterSpam filterSpam;
    FilterToLong filterToLong;

    /**
     * Для работы с каждым элементом массива, нужно использовать цикл for-each
     * Хочется заметить, что тут мы ничего не знаем, какие конкретно нам объекты переданы, знаем только то,
     * что в них реализован интерфейс TextAnalyzer
     */
    public TextFilterManager(TextAnalyzer[] filters) {
        for (TextAnalyzer i : filters) {

        }
    }

    /**
     * Если переменная текст никуда не ссылается, то это означает, что не один фильтр не сработал
     */
    public FilterType analyze(String text) {
        if (text == null || text.isEmpty()) {
            return FilterType.GOOD;
        } else if (text.contains(filterNegative.getSadStr1()) || text.contains(filterNegative.getSadStr2()) || text.contains(filterNegative.getSadStr3())) {
            return FilterType.NEGATIVE_TEXT;
        }
        return null;
    }


}

class FilterSpam implements TextAnalyzer {
    String[] badStrings;

    public FilterSpam(String[] badStrings) {
        this.badStrings = badStrings;
    }
}

class FilterToLong implements TextAnalyzer {
    private long maxQuantity;

    public FilterToLong(long maxQuantity) {
        this.maxQuantity = maxQuantity;
    }
    long getMaxQuantity() {
        return maxQuantity;
    }
}

class FilterNegative implements TextAnalyzer {
    private String sadStr1;
    private String sadStr2;
    private String sadStr3;

    public FilterNegative() {
        sadStr1 = "=(";
        sadStr2 = ":(";
        sadStr3 = ":|";
    }

    String getSadStr1() {
        return sadStr1;
    }
    String getSadStr2() {
        return sadStr2;
    }
    String getSadStr3() {
        return sadStr3;
    }
}