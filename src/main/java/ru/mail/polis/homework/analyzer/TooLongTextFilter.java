package ru.mail.polis.homework.analyzer;

public class TooLongTextFilter implements TextAnalyzer {
    private final static FilterType typeFilter = FilterType.TOO_LONG;
    private static long maxLength;

    public TooLongTextFilter(long length) {
        maxLength = length;
    }

    @Override
    public FilterType getFilterType() {
        return typeFilter;
    }

    @Override
    public FilterType analyze(String str) {
        if (str.length() > maxLength) {
            return typeFilter;
        }
        return FilterType.GOOD;
    }
}
