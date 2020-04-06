package ru.mail.polis.homework.analyzer;

public class TooLongTextFilter implements TextAnalyzer {
    private long maxLength;

    public TooLongTextFilter(long length) {
        this.maxLength = length;
    }

    @Override
    public FilterType getTypeOfFilter(String str) {
        if (str.length() > maxLength) {
            return FilterType.TOO_LONG;
        }
        return FilterType.GOOD;
    }
}
