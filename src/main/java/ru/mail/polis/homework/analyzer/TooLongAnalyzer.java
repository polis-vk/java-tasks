package ru.mail.polis.homework.analyzer;

public class TooLongAnalyzer implements TextAnalyzer {
    private final static int SIGNIFICANCE = 2;
    private final long maxLength;

    public TooLongAnalyzer(long maxLength) {
        this.maxLength = maxLength;
    }

    @Override
    public int getSignificance() {
        return SIGNIFICANCE;
    }

    @Override
    public FilterType analyze(String text) {
        if (text.length() > maxLength) {
            return FilterType.TOO_LONG;
        }
        return FilterType.GOOD;
    }
}
