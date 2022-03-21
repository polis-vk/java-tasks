package ru.mail.polis.homework.analyzer;

/**
 * Типы фильтров (2 тугрика)
 */
public enum FilterType {
    TOO_LONG (3),
    SPAM (4),
    NEGATIVE_TEXT (2),
    CUSTOM (1),
    GOOD ( 0);

    private final int priority;

    FilterType(int priority) {
        this.priority = priority;
    }

    public int getPriority () {
        return priority;
    }
}
