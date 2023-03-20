package ru.mail.polis.homework.analyzer;

/**
 * Типы фильтров (2 тугрика)
 */
public enum FilterType {
    SPAM(1),
    TOO_LONG(2),
    NEGATIVE_TEXT(3),
    CUSTOM(4),
    GOOD(0);
    private final int priority;

    FilterType(int priority) {
        this.priority = priority;
    }

    public int getPriority() {
        return priority;
    }
}
