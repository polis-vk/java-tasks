package ru.mail.polis.homework.analyzer;

/**
 * Типы фильтров (2 балла)
 */
public enum FilterType {
    SPAM(4),
    TOO_LONG(3),
    NEGATIVE_TEXT(2),
    CUSTOM(1),
    GOOD(0);

    private final long priority;

    FilterType(long priority) {
        this.priority = priority;
    }

    public long getPriority() {
        return priority;
    }
}
