package ru.mail.polis.homework.analyzer;

/**
 * Типы фильтров (2 тугрика)
 */
public enum FilterType {
    GOOD(0), SPAM(1), TOO_LONG(2), NEGATIVE_TEXT(3), EXCESSIVE_SPACES(4);
    final private int num;

    FilterType(int num) {
        this.num = num;
    }

    public int getNum() {
        return num;
    }
}
