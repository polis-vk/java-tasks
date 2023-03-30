package ru.mail.polis.homework.analyzer;

public class TooLongAnalyzer implements TextAnalyzer {
    private final long length;

    TooLongAnalyzer(long maxLength) {
        this.length = maxLength;
    }

    @Override
    public FilterType getType() {
        return FilterType.TOO_LONG;
    }

    @Override
    public boolean filterWorked(String str) {
        return this.length <= str.length();
    }
}

