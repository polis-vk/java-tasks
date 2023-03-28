package ru.mail.polis.homework.analyzer;

/**
 * Типы фильтров (2 тугрика)
 */
public enum FilterType {
    GOOD(0),
    SPAM(1),
    TOO_LONG(2),
    NEGATIVE_TEXT(3),
    TOO_MUSH_WORDS(4);

    private final int order;

    FilterType(int order) {
        this.order = order;
    }

    public int getOrder() {
        return order;
    }
}
