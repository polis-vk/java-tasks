package ru.mail.polis.homework.analyzer;

/**
 * Типы фильтров (2 балла)
 */
public enum FilterType {
    SPAM(4),
    TOO_LONG(3),
    NEGATIVE_TEXT(2),
    CUSTOM_EXTRA_SPACES(1),
    GOOD(0);

    private final int filterPriority;
    FilterType(int priority) {
        this.filterPriority = priority;
    }

    public int getPriority() {
        return this.filterPriority;
    }
}
