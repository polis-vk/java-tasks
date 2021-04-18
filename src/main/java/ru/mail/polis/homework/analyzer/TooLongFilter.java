package ru.mail.polis.homework.analyzer;

public class TooLongFilter implements TextAnalyzer {
    private final long maxLength;

    public TooLongFilter(long maxLength) {
        this.maxLength = maxLength;
    }

    @Override
    public FilterType filterType() {
        return FilterType.TOO_LONG;
    }

    @Override
    public boolean analyzeText(String text) {
        return text.length() > maxLength;
    }
}
