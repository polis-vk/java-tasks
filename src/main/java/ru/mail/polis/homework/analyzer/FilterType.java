package ru.mail.polis.homework.analyzer;

/**
 * Типы фильтров (2 балла)
 */
public enum FilterType {
    //чем больше число, тем выше приоритет
    SPAM(4),
    TOO_LONG(3),
    NEGATIVE_TEXT(2),
    TOO_MUCH_SYMBOL(1),
    GOOD(Long.MIN_VALUE);

    private final long priority;


    FilterType(long priority) {
        this.priority = priority;
    }

    public long getPriority() {
        return priority;
    }
}
