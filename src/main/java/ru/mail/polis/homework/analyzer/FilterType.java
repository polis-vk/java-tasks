package ru.mail.polis.homework.analyzer;

/**
 * Типы фильтров (2 балла)
 */
public enum FilterType {
    TOO_LONG(2),
    SPAM(1),
    NEGATIVE_TEXT(3),
    CUSTOM(4),
    GOOD(0);

    private final int priority;

    FilterType(int priority) {
        this.priority = priority;
    }

    public int priority() {
        return priority;
    }
}
