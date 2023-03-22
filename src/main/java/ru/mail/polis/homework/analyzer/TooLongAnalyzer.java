package ru.mail.polis.homework.analyzer;

public class TooLongAnalyzer implements TextAnalyzer {
    long maxLength;
    private static final FilterType filterType = FilterType.TOO_LONG;

    TooLongAnalyzer(long maxLength) {
        this.maxLength = maxLength;
    }

    @Override
    public boolean isTextCorrect(String text) {
        return text.length() <= maxLength;
    }

    @Override
    public FilterType getFilterType() {
        return filterType;
    }
}