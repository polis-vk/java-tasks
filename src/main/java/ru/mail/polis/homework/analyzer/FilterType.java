package ru.mail.polis.homework.analyzer;

/**
 * Типы фильтров (2 тугрика)
 */
public enum FilterType {
    GOOD(0),
    SPAM(1),
    TOO_LONG(2),
    NEGATIVE_TEXT(3),
    MARKS(4);

    private final int number;

    FilterType(int number) {
        this.number = number;
    }

    public int getNumber() {
        return number;
    }
}
