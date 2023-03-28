package ru.mail.polis.homework.analyzer;

public class TooLongWordAnalyzer implements TextAnalyzer {
    private final long maxLength;

    public TooLongWordAnalyzer(long maxLength) {
        this.maxLength = maxLength;
    }

    @Override
    public boolean isAnalyzeTrue(String text) {
        return text.length() > maxLength;
    }

    @Override
    public FilterType getFilterType() {
        return FilterType.TOO_LONG;
    }
}
