package ru.mail.polis.homework.analyzer;

public class TooLongAnalyzer implements TextAnalyzer {
    private final long maxLength;
    private static final int PRIORITY = 2;

    public TooLongAnalyzer(long maxLength) {
        this.maxLength = maxLength;
    }

    @Override
    public FilterType analyze(String text) {
        if (text.length() > maxLength) {
            return FilterType.TOO_LONG;
        }
        return FilterType.GOOD;
    }

    @Override
    public int getPriority() {
        return PRIORITY;
    }
}
