package ru.mail.polis.homework.analyzer;

public class TooLongAnalyzer implements TextAnalyzer {
    private final long maxLength;
    private static final FilterType type = FilterType.TOO_LONG;

    public TooLongAnalyzer(long length) {
        maxLength = length;
    }

    @Override
    public FilterType filtering(String text) {
        return text.length() > maxLength ? FilterType.TOO_LONG : FilterType.GOOD;
    }

    @Override
    public FilterType getFilterType() {
        return type;
    }

    public long getMaxLength() {
        return maxLength;
    }
}

