package ru.mail.polis.homework.analyzer;

public class TooLongAnalyzer implements TextAnalyzer {
    private final long maxLength;

    TooLongAnalyzer(long maxLength) {
        this.maxLength = maxLength;
    }

    @Override
    public boolean isAnalyze(String str) {
        return str.length() <= maxLength;
    }

    @Override
    public FilterType getType() {
        return FilterType.TOO_LONG;
    }
}
