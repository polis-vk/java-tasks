package ru.mail.polis.homework.analyzer;

/**
 * Типы фильтров (2 тугрика)
 */
public enum FilterType {
    SPAM(0),
    TOO_LONG(1),
    NEGATIVE_TEXT(2),
    CUSTOM(3),
    GOOD(4);

    final int priority;

    FilterType(int priority) {
        this.priority = priority;
    }
}

