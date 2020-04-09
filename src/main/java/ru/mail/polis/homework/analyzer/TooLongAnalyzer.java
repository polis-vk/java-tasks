package ru.mail.polis.homework.analyzer;

public class TooLongAnalyzer implements TextAnalyzer {
    private long maximalLength;

    public TooLongAnalyzer(long maximalLength) {
        this.maximalLength = maximalLength;
    }

    public boolean isTrue(String text) {
        return text.length() > maximalLength;
    }

    public FilterType getFilterType() {
        return FilterType.TOO_LONG;
    }

}