package ru.mail.polis.homework.analyzer;

/**
 * Типы фильтров (2 тугрика)
 */
public enum FilterType {
    TOO_LONG(4), SPAM(5), NEGATIVE_TEXT(3), GOOD(1), NO_SIMILAR_STRING(2);
    public final int mass;

    FilterType(int mass) {
        this.mass = mass;
    }


}
