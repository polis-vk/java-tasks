package ru.mail.polis.homework.analyzer;

/**
 * Типы фильтров (2 тугрика)
 */
public enum FilterType {
    GOOD(-1),
    CUSTOM(1),
    NEGATIVE_TEXT(2),
    TOO_LONG(3),
    SPAM(4);


    private final int type;

    FilterType(int type) {
        this.type = type;
    }

    public int getType() {
        return type;
    }
}
