package ru.mail.polis.homework.analyzer;

/**
 * Типы фильтров (2 тугрика)
 */
public enum FilterType {
    SPAM(0),
    TOO_LONG(1),
    NEGATIVE_TEXT(2),
    TOO_SHORT_LONG(3),
    GOOD(4);
    int priority;

    FilterType(int priority) {
        this.priority = priority;
    }
}
