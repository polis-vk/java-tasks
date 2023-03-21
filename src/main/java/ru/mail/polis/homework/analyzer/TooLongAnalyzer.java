package ru.mail.polis.homework.analyzer;

public class TooLongAnalyzer implements TextAnalyzer {
    long maxLength;

    TooLongAnalyzer(long maxLength) {
        this.maxLength = maxLength;
    }

    public FilterType analyze(String text) {
        if (text.length() > maxLength) {
            return FilterType.TOO_LONG_FILTER;
        }
        return FilterType.GOOD;
    }
}
