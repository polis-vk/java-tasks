package ru.mail.polis.homework.analyzer;

public class TooLongAnalyzer implements TextAnalyzer {
    private final long maxLength;
    private static final FilterType FILTER_TYPE = FilterType.TOO_LONG;

    public TooLongAnalyzer(long maxLength) {
        this.maxLength = maxLength;
    }

    @Override
    public boolean isTextCorrect(String text) {
        return text.length() <= maxLength;
    }

    @Override
    public FilterType getFilterType() {
        return FILTER_TYPE;
    }
}