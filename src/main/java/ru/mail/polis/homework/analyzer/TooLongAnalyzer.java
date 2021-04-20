package ru.mail.polis.homework.analyzer;

public class TooLongAnalyzer implements TextAnalyzer {
    private final long MAX_LENGTH;
    private final int PRIORITY;

    public TooLongAnalyzer(long maxLength, int priority) {
        this.MAX_LENGTH = maxLength;
        this.PRIORITY = priority;
    }

    @Override
    public int getPriority() {
        return PRIORITY;
    }

    @Override
    public FilterType getFilterType() {
        return FilterType.TOO_LONG;
    }

    @Override
    public boolean isPassed(String str) {
        return str != null && str.length() <= MAX_LENGTH;
    }
}
