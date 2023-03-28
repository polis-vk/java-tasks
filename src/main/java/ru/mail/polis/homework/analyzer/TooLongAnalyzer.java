package ru.mail.polis.homework.analyzer;

public class TooLongAnalyzer implements TextAnalyzer {
    private final long maxLength;

    TooLongAnalyzer(long maxLength) {
        this.maxLength = maxLength;
    }

    public FilterType analyze(String text) {
        if (text.length() > getMaxLength()) {
            return FilterType.TOO_LONG;
        }
        return FilterType.GOOD;
    }

    public long getMaxLength() {
        return maxLength;
    }
}
