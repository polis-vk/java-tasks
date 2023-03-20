package ru.mail.polis.homework.analyzer;


import org.w3c.dom.Text;

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
    TextAnalyzer[] filters;

    /**
     * Для работы с каждым элементом массива, нужно использовать цикл for-each
     * Хочется заметить, что тут мы ничего не знаем, какие конкретно нам объекты переданы, знаем только то,
     * что в них реализован интерфейс TextAnalyzer
     */
    public TextFilterManager(TextAnalyzer[] filters) {
        this.filters = filters;
    }

    /**
     * Если переменная текст никуда не ссылается, то это означает, что не один фильтр не сработал
     */
    public FilterType analyze(String text) {
        if (text == null) {
            return FilterType.GOOD;
        }
        for (TextAnalyzer i : this.filters) {
            if (i.trigger(text)) {
                return i.getType();
            }
        }
        return FilterType.GOOD;
    }


}

class FilterSpam implements TextAnalyzer {
    String[] badStrings;

    public FilterSpam(String[] badStrings) {
        this.badStrings = badStrings;
    }

    @Override
    public FilterType getType() {
        return FilterType.SPAM;
    }

    @Override
    public boolean trigger(String str) {
        for (String i : this.badStrings) {
            if (str.contains(i)) {
                return true;
            }
        }
        return false;
    }
}

class FilterToLong implements TextAnalyzer {
    private final long maxQuantity;

    public FilterToLong(long maxQuantity) {
        this.maxQuantity = maxQuantity;
    }

    long getMaxQuantity() {
        return maxQuantity;
    }

    @Override
    public FilterType getType() {
        return FilterType.TOO_LONG;
    }

    @Override
    public boolean trigger(String str) {
        if (str.length() > maxQuantity) {
            return true;
        }
        return false;
    }
}

class FilterNegative implements TextAnalyzer {
    private final String sadStr1;
    private final String sadStr2;
    private final String sadStr3;

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

    @Override
    public FilterType getType() {
        return FilterType.NEGATIVE_TEXT;
    }

    @Override
    public boolean trigger(String str) {
        if (str.contains(sadStr1) || str.contains(sadStr2) || str.contains(sadStr3)) {
            return true;
        }
        return false;
    }
}

class FilterDigit implements TextAnalyzer {
    @Override
    public FilterType getType() {
        return FilterType.CUSTOM;
    }

    @Override
    public boolean trigger(String str) {
        for (int counter = 0; counter < str.length(); counter++) {
            if (Character.isDigit(str.charAt(counter))) {
                return true;
            }
        }
        return false;
    }
}