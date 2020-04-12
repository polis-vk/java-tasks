package ru.mail.polis.homework.analyzer;

public class TooLongTextFilter implements TextAnalyzer {
    private final static FilterType TYPE_FILTER = FilterType.TOO_LONG;
    private static long maxLength;

    public TooLongTextFilter(long length) {
        maxLength = length;
    }

    @Override
    public FilterType getFilterType() {
        return TYPE_FILTER;
    }

    @Override
    public FilterType analyze(String str) {
        if (str.length() > maxLength) {
            return TYPE_FILTER;
        }
        return FilterType.GOOD;
    }
}
