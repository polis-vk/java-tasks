package ru.mail.polis.homework.analyzer;

/**
 * Типы фильтров (2 тугрика)
 */
public enum FilterType {
    GOOD(0),
    TOO_MUSH_WORDS(4),
    NEGATIVE_TEXT(3),
    SPAM(1),
    TOO_LONG(2);
    final int order;
    FilterType(int order) {
        this.order = order;
    }



}
