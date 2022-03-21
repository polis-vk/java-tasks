package ru.mail.polis.homework.analyzer;

public enum FilterType {
    TOO_LONG(2),
    GOOD(0),
    SPAM(1),
    NEGATIVE_TEXT(3),
    CUSTOM(4);

    private final int priority;

    FilterType(int priority) {
        this.priority = priority;
    }

    public int getPriority() {
        return priority;
    }
}
