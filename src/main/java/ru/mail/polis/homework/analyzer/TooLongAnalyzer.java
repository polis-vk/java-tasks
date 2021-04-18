package ru.mail.polis.homework.analyzer;

public class TooLongAnalyzer implements TextAnalyzer {
    private final long maxLength;

    @Override
    public int priority() {
        return 2;
    }

    @Override
    public FilterType filterType() {
        return FilterType.TOO_LONG;
    }

    public TooLongAnalyzer(long maxLength) {
        this.maxLength = maxLength;
    }


    @Override
    public FilterType analyze(String text) {
        return text.length() > maxLength ? filterType() : FilterType.GOOD;
    }
}
