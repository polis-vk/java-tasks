package ru.mail.polis.homework.analyzer;

/**
 * Типы фильтров (2 тугрика)
 */
public enum FilterType {
    GOOD(4),
    TOO_LONG(1),
    SPAM(0),
    NEGATIVE_TEXT(2),
    ENGLISH_TEXT(3);

    private final int priority;

    final int priorityInfo() {
        return this.priority;
    }

    FilterType(int priority) {
        this.priority = priority;
    }
}
