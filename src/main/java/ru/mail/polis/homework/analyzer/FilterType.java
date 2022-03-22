package ru.mail.polis.homework.analyzer;

/**
 * Типы фильтров (2 тугрика)
 */
public enum FilterType {
    SPAM(0),
    TOO_LONG(1),
    NEGATIVE_TEXT(2),
    HAS_SPACE_AFTER_POINT(3),
    GOOD(4);

    private final int order;

    FilterType(int order) {
        this.order = order;
    }

    public int getPriority() {
        return order;
    }
}
