package ru.mail.polis.homework.analyzer;

/**
 * Типы фильтров (2 тугрика)
 */
public enum FilterType {
    SPAM(5),
    TOO_LONG(4),
    NEGATIVE_TEXT(3),
    CUSTOM(2),
    GOOD(1);

    private final int priority;

    FilterType(int priority) {
        this.priority = priority;
    }

    public int getPriority() {
        return priority;
    }
}
