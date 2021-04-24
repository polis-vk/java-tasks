package ru.mail.polis.homework.analyzer;

public class TooLongAnalyzer implements TextAnalyzer {
    private final long maxLength;

    public TooLongAnalyzer(long maxLength) {
        this.maxLength = maxLength;
    }

    @Override
    public FilterType analyze(String text) {
        return text.length() > maxLength ? FilterType.TOO_LONG : FilterType.GOOD;
    }

    @Override
    public FilterType getType() {
        return FilterType.TOO_LONG;
    }
}
