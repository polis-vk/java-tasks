package ru.mail.polis.homework.analyzer;

public class TooLongFilter implements TextAnalyzer {
    private long maxLength;

    public TooLongFilter(long maxLength) {
        this.maxLength = maxLength;
    }

    @Override
    public FilterType analyze(String text) {
        if (text.length() > maxLength) {
            return FilterType.TOO_LONG;
        }
        return FilterType.GOOD;
    }

    @Override
    public FilterType priority() {
        return FilterType.TOO_LONG;
    }
}
