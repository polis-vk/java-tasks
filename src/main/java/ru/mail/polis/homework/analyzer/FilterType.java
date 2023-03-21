package ru.mail.polis.homework.analyzer;

import jdk.nashorn.internal.ir.SplitReturn;

/**
 * Типы фильтров (2 тугрика)
 */
public enum FilterType {
    TOO_LONG_FILTER("TOO_LONG"),
    SPAM("SPAM"),
    NEGATIVE_TEXT("NEGATIVE_TEXT"),
    GOOD("GOOD");

    FilterType(String title) {
        this.TITLE = title;
    }


    final String TITLE;

    @Override
    public String toString() {
        return TITLE;
    }
}
