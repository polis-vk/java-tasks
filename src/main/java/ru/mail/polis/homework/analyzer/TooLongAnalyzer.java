package ru.mail.polis.homework.analyzer;

public class TooLongAnalyzer implements TextAnalyzer {
    private final long maxLength;

    public TooLongAnalyzer(long maxLength) {
        this.maxLength = maxLength;
    }

    @Override
    public boolean problemDetected(String text) {
        return text.length() > maxLength;
    }

    @Override
    public FilterType getFilterType() {
        return FilterType.TOO_LONG;
    }
}