package ru.mail.polis.homework.analyzer;

public class TooLongAnalyzer implements TextAnalyzer {
    private final long maxLength;

    TooLongAnalyzer(long maxLength) {
        this.maxLength = maxLength;
    }

    @Override
    public boolean filterSuccess(String text) {
        return text.length() > maxLength;
    }

    @Override
    public FilterType filterType() {
        return FilterType.TOO_LONG;
    }
}
