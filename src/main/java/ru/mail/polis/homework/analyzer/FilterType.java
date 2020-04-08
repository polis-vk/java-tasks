package ru.mail.polis.homework.analyzer;

/**
 * Типы фильтров (2 балла)
 */
public enum FilterType {
    SPAM(1),
    TOO_LONG(2),
    NEGATIVE_TEXT(3),
    CAPS(0),
    GOOD(Integer.MAX_VALUE);

    private int priority;

    private FilterType(int priority) {
        this.priority = priority;
    }

    public int getPriority() {
        return priority;
    }

}
