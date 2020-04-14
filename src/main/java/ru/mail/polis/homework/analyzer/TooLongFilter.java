package ru.mail.polis.homework.analyzer;

public class TooLongFilter implements TextAnalyzer {
    long maxLength;

    public TooLongFilter(long maxLength) {
        this.maxLength = maxLength;
    }

    @Override
    public boolean filterIsPassed(String text) {
        return text.length() > maxLength;
    }

    @Override
    public FilterType getFilterType() {
        return FilterType.TOO_LONG;
    }
}
