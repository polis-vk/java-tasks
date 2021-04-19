package ru.mail.polis.homework.analyzer;

public class TooLongAnalyzer implements TextAnalyzer {
    private final int priority;

    private final long maxLength;

    public TooLongAnalyzer(long maxLength, int priority) {
        this.maxLength = maxLength;
        this.priority = priority;
    }

    @Override
    public FilterType getType() {
        return FilterType.TOO_LONG;
    }

    @Override
    public final int getPriority() {
        return priority;
    }

    @Override
    public boolean isValid(String text) {
        return text.length() <= maxLength;
    }
}
