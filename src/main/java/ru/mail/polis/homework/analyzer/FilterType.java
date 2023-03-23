package ru.mail.polis.homework.analyzer;

/**
 * Типы фильтров (2 тугрика)
 */
public enum FilterType {
    GOOD(0),
    SPAM(1),
    TOO_LONG(2),
    NEGATIVE_TEXT(3),
    CAT_FRIENDLY(4);

    public final int priority;

    FilterType(int priority) {
        this.priority = priority;
    }
}
