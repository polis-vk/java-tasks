package ru.mail.polis.homework.analyzer;

/**
 * Типы фильтров (2 тугрика)
 */
public enum FilterType {
    SPAM(0),
    TOO_LONG(1),
    NEGATIVE_TEXT(2),
    ENGLISH_TEXT(3),
    GOOD(4);

    FilterType(int priority) {
        this.priority = priority;
    }

    FilterType() {
    }

    int priority;
}