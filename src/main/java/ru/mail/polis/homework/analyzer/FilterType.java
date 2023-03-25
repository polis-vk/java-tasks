package ru.mail.polis.homework.analyzer;

/**
 * Типы фильтров (2 тугрика)
 */
public enum FilterType {
    GOOD(0),
    SPAM(1),
    TOO_LONG(2),
    NEGATIVE_TEXT(3),
    CAPS(4);

    private int type;

    FilterType(int type) {
    this.type = type;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
