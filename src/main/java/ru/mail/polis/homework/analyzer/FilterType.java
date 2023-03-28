package ru.mail.polis.homework.analyzer;

/**
 * Типы фильтров (2 тугрика)
 */
public enum FilterType implements Comparable<FilterType>{
    SPAM,
    TOO_LONG,
    NEGATIVE_TEXT,
    REPEATING_TEXT,
    GOOD
}
