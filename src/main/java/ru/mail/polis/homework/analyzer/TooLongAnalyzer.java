package ru.mail.polis.homework.analyzer;

public class TooLongAnalyzer implements TextAnalyzer {
    private final long maxLength;
    private final int priority = 2;

    public TooLongAnalyzer(long maxLength) {
        this.maxLength = maxLength;
    }

    @Override
    public boolean isNotCorrectString(String str) {
        return str.length() > maxLength;
    }

    @Override
    public int getPriority() {
        return priority;
    }

    @Override
    public FilterType getType() {
        return FilterType.TOO_LONG;
    }

}


