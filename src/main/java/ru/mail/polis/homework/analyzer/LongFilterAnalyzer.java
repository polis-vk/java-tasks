package ru.mail.polis.homework.analyzer;

public class LongFilterAnalyzer implements TextAnalyzer {
    private final long maxLength;

    LongFilterAnalyzer(long maxLength) {
        this.maxLength = maxLength;
    }

    @Override
    public FilterType getType() {
        return FilterType.TOO_LONG;
    }

    @Override
    public boolean isFilterFailed(String text) {
        return text.length() > maxLength;
    }
}
