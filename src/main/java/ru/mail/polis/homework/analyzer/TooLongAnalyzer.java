package ru.mail.polis.homework.analyzer;

public class TooLongAnalyzer implements TextAnalyzer {
    long maxLength;
    private static final byte priority = 2;

    TooLongAnalyzer(long maxLength) {
        this.maxLength = maxLength;
    }

    @Override
    public boolean isTextCorrect(String text) {
        return text.length() <= maxLength;
    }
    @Override
    public FilterType getFilterType() {
        return FilterType.TOO_LONG;
    }
    @Override
    public byte getPriority() {
        return priority;
    }
}