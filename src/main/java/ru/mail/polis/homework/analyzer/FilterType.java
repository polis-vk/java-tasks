package ru.mail.polis.homework.analyzer;

/**
 * Типы фильтров (2 балла)
 */
public enum FilterType {
    SPAM(1),
    TOO_LONG(2),
    NEGATIVE_TEXT(3),
    GOOD(5),
    ORTHOGRAPHIC_ERR(4);
    int priority;

    FilterType(int priority) {
        this.priority = priority;
    }
}
