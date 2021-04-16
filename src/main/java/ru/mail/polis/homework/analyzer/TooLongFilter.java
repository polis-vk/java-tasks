package ru.mail.polis.homework.analyzer;

public class TooLongFilter implements TextAnalyzer {
    private static final byte priority = 2;
    private static final FilterType filterType = FilterType.TOO_LONG;
    private final long maxLength;

    public TooLongFilter(long maxLength) {
        this.maxLength = maxLength;
    }

    @Override
    public byte priority() {
        return priority;
    }

    @Override
    public FilterType filterType() {
        return filterType;
    }

    @Override
    public boolean analyzeText(String text) {
        return text.length() > maxLength;
    }
}
