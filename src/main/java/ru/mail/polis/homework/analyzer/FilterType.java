package ru.mail.polis.homework.analyzer;

/**
 * Типы фильтров (2 тугрика)
 */
public enum FilterType {
    SPAM(2),
    TOO_LONG(3),
    NEGATIVE_TEXT(4),
    CUSTOM(5),
    GOOD(1);

    FilterType(int priority) {
        this.priority = priority;
    }

    public int getPriority()
    {
        return priority;
    }

    private int priority;
}
