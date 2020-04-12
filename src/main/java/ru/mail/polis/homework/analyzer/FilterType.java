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

    FilterType(int value) {
        this.value = value;
    }

    private int value;

    public int getValue() {
        return value;
    }
}

