package ru.mail.polis.homework.analyzer;

/**
 * Типы фильтров (2 тугрика)
 */
public enum FilterType {
    TOO_LONG(1),
    SPAM(2),
    NEGATIVE_TEXT(3),
    CYRILLIC_TEXT(4),
    GOOD(5);

    private final int priority;

    FilterType(int priority) {
        this.priority = priority;
    }

    public int getPriority() {
        return priority;
    }
}
