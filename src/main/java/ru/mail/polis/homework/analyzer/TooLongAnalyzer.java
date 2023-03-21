package ru.mail.polis.homework.analyzer;

public class TooLongAnalyzer implements TextAnalyzer {
    private final long maxLength;

    public TooLongAnalyzer(long maxLength) {
        this.maxLength = maxLength;
    }

    public long getMaxLength() {
        return maxLength;
    }

    @Override
    public int getPriority() {
        return 2;
    }

    @Override
    public FilterType getFilterType(String str) {
        if (str.length() > maxLength) {
            return FilterType.TOO_LONG;
        }
        return FilterType.GOOD;
    }
}
