package ru.mail.polis.homework.analyzer;

public class TooLongTextAnalyzer implements TextAnalyzer {
    private final long maxLength;

    public TooLongTextAnalyzer(long maxLength) {
        this.maxLength = maxLength;
    }

    @Override
    public FilterType getFilterType() {
        return FilterType.TOO_LONG;
    }

    @Override
    public boolean isMatchFilter(String text) {
        return text.length() > maxLength;
    }
}
