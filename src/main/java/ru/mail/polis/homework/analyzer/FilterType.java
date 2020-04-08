package ru.mail.polis.homework.analyzer;

/**
 * Типы фильтров (2 балла)
 */
public enum FilterType {
    SPAM(1),
    TOO_LONG(2),
    NEGATIVE_TEXT(3),
    CUSTOM(4),
    GOOD(5);

    private final int PRIORITY;

    FilterType(int priority) {
        this.PRIORITY = priority;
    }

    public int getPriority() {
        return PRIORITY;
    }

}
