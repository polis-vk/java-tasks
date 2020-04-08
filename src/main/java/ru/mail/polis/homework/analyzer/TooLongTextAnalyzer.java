package ru.mail.polis.homework.analyzer;

public class TooLongTextAnalyzer implements TextAnalyzer {

    private long maxLength;

    TooLongTextAnalyzer(long maxLength) {
        this.maxLength = maxLength;
    }

    @Override
    public FilterType analyze(String str) {
        if (str == null) {
            return FilterType.GOOD;
        }
        if (str.length() > maxLength) {
            return getFilterType();
        }
        return FilterType.GOOD;
    }

    @Override
    public FilterType getFilterType() {
        return FilterType.TOO_LONG;
    }
}
