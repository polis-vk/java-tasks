package ru.mail.polis.homework.analyzer;

/**
 * Типы фильтров (2 тугрика)
 */
public enum FilterType {
    GOOD(1),
    TOO_LONG(4),
    SPAM(5),
    NEGATIVE_TEXT(3),
    TOO_MANY_WORDS(2);
    private final int priority;

    FilterType(int priority) {
        this.priority = priority;
    }

    public int getPriority() {
        return priority;
    }
}
