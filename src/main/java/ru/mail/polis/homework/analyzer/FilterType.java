package ru.mail.polis.homework.analyzer;

/**
 * Типы фильтров (2 балла)
 */
public enum FilterType {
    GOOD(0),
    SPAM(1),
    TOO_LONG(2),
    NEGATIVE_TEXT(3),
    SPY_MESSAGE(4);

    private int numVal;

    FilterType(int numVal) {
        this.numVal = numVal;
    }

    public int getPriority() {
        return numVal;
    }
}
