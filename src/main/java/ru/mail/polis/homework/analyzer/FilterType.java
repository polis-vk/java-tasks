package ru.mail.polis.homework.analyzer;

/**
 * Типы фильтров (2 тугрика)
 */
public enum FilterType {
    TOO_LONG(1),
    SPAM(2),
    NEGATIVE_TEXT(3),
    VALID_EMAIL(4),
    GOOD(Integer.MAX_VALUE);

    private final int priority;

    FilterType(int priority){
        this.priority = priority;
    }

    public int getPriority(){
        return priority;
    }
}
