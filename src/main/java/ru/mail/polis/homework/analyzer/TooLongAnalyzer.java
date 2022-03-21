package ru.mail.polis.homework.analyzer;

public class TooLongAnalyzer implements TextAnalyzer {

    private final long maxLength;

    TooLongAnalyzer(long maxLength) {
        this.maxLength = maxLength;
    }

    @Override
    public boolean filterOfType(String text) {
        if (text.length() > maxLength) {
            return true;
        }
        return false;
    }

    public FilterType getType() {
        return FilterType.TOO_LONG;
    }
}
