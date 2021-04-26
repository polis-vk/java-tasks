package ru.mail.polis.homework.analyzer;

/**
 * Типы фильтров (2 балла)
 */
public enum FilterType {
    SPAM(1),
    TOO_LONG(2),
    NEGATIVE_TEXT(3),
    NOT_OFFICIAL(4),
    GOOD(5);

    private final int rank;

    FilterType(int prior) {
        this.rank = prior;
    }

    public int getRank() {
        return this.rank;
    }
}
