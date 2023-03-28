package ru.mail.polis.homework.analyzer;

public class TooLongTextAnalyzer implements TextAnalyzer {
    private final long maxLength;

    public TooLongTextAnalyzer(long maxLength) {
        this.maxLength = maxLength;
    }

    @Override
    public boolean analyzeFilterStatus(String text) {
        return maxLength < text.length();
    }

    @Override
    public FilterType getFilterType() {
        return FilterType.TOO_LONG;
    }
}
