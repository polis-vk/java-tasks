package ru.mail.polis.homework.analyzer;

/**
 * Типы фильтров (2 тугрика)
 */
public enum FilterType {
    GOOD(1),
    SPAM(2),
    TOO_LONG(3),
    NEGATIVE_TEXT(4),
    CAPITAL_CHAR(5);
    private final int priority;
    public int getPriority() { return priority; }
    FilterType(int priority) { this.priority = priority; }
}

