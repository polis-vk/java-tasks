package ru.mail.polis.homework.analyzer;

/**
 * Типы фильтров (2 балла)
 */
public enum FilterType {
    SPAM(0),
    TOO_LONG(1),
    NEGATIVE_TEXT(2),
    CUSTOM(3),
    GOOD(4);

    private final int PRIORITY;

     FilterType(int priority) {
        this.PRIORITY = priority;
    }

    public int getPriority() {
        return this.PRIORITY;
    }
}
