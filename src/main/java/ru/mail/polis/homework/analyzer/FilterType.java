package ru.mail.polis.homework.analyzer;

/**
 * Типы фильтров (2 тугрика)
 */
public enum FilterType {
    GOOD(0),
    SPAM(1),
    TOO_LONG(2),
    NEGATIVE_TEXT(3),
    SOFT_TEXT(4),
    ;

    public final int priority;

    FilterType(int i) {
        this.priority = i;
    }
}
