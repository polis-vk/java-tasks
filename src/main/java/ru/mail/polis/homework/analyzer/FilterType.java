package ru.mail.polis.homework.analyzer;

/**
 * Типы фильтров (2 балла)
 */
public enum FilterType {
    SPAM(1),
    TOO_LONG(2),
    NEGATIVE_TEXT(3),
    CUSTOM(4),
    GOOD(null);

    public Integer getPriority() {
        return priority;
    }

    private final Integer priority;

    FilterType(Integer priority) {
        this.priority = priority;
    }

}
