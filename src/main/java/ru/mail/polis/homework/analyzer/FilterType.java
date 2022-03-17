package ru.mail.polis.homework.analyzer;

/**
 * Типы фильтров (2 тугрика)
 */
public enum FilterType {
    GOOD(5),
    SPAM(1),
    TOO_LONG(2),
    NEGATIVE_TEXT(3),
    CAPSLOCK(4);

    private final int priority;

    FilterType(int priority) {
        this.priority = priority;
    }

    public int getPriority() {
        return priority;
    }
}
