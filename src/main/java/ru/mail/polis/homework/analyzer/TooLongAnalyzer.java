package ru.mail.polis.homework.analyzer;

public class TooLongAnalyzer implements TextAnalyzer {
    private final long maxLength;

    public TooLongAnalyzer(long maxLength) {
        this.maxLength = maxLength;
    }

    public boolean check(String txt) {
        if (txt.length() > maxLength) {
            return true;
        }
        return false;
    }

    public FilterType getFilterType() {
        return FilterType.TOO_LONG;
    }
}