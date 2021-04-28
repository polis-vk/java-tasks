package ru.mail.polis.homework.analyzer;

public class TooLongAnalyzer implements TextAnalyzer {
    private final long maxLength;

    public TooLongAnalyzer(long maxLength) {
        this.maxLength = maxLength;
    }

    @Override
    public FilterType getAnalyzerType() {
        return FilterType.TOO_LONG;
    }

    @Override
    public boolean successfullyAnalyzed(String text) {
        return text.length() > this.maxLength;
    }
}
