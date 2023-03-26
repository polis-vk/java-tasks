package ru.mail.polis.homework.analyzer;

/**
 * Типы фильтров (2 тугрика)
 */
public enum FilterType {
    GOOD(0),
    SPAM(1),
    TOO_LONG(2),
    NEGATIVE_TEXT(3),
    CUSTOM(4);
    private final int rate;

    FilterType(int rate) {
        this.rate = rate;
    }

    public int getRate() {
        return this.rate;
    }
}
