package ru.mail.polis.homework.analyzer;

/**
 * Типы фильтров (2 тугрика)
 */
public enum FilterType {
    TOO_LONG(2),
    SPAM(3),
    NEGATIVE_TEXT(1),
    REPEATING_TEXT(0),
    GOOD(-1);

    FilterType(int priority) {
        this.priority = priority;
    }

    private int priority;

    public int getPriority() {
        return priority;
    }

    @Override
    public String toString() {
        return name();
    }
}
