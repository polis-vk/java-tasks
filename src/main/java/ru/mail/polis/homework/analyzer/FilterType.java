package ru.mail.polis.homework.analyzer;

/**
 * Типы фильтров (2 балла)
 */
public enum FilterType {
    TOO_LONG(2),
    SPAM(1),
    NEGATIVE_TEXT(3),
    SPACE_LENGTH(4),
    GOOD(0);

    public int priority;

    FilterType(int priority) {
        this.priority = priority;
    }
}
