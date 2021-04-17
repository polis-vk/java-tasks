package ru.mail.polis.homework.analyzer;

public class TooLongAnalyzer implements TextAnalyzer {
    long maxLength;

    public TooLongAnalyzer(long maxLength) {
        this.maxLength = maxLength;
    }

    @Override
    public boolean isNotCorrectString(String str) {
        return str.length() > maxLength;
    }

    @Override
    public int getPriority() {
        return 2;
    }

    @Override
    public FilterType getType() {
        return FilterType.TOO_LONG;
    }
}
