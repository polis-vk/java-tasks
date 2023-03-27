package ru.mail.polis.homework.analyzer;

/**
 * Типы фильтров (2 тугрика)
 */
public enum FilterType {
    GOOD(0),
    SPAM(1),
    TOO_LONG(2),
    NEGATIVE_TEXT(3),
    COOK(4);

    private final int significance;

    FilterType(int significance) {
        this.significance = significance;
    }

    public int getSignificance() {
        return significance;
    }
}
