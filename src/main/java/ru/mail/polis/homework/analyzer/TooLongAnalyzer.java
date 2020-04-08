package ru.mail.polis.homework.analyzer;

public class TooLongAnalyzer implements TextAnalyzer {
    private long maxLength;

    TooLongAnalyzer(long maxLength) {
        this.maxLength = maxLength;
    }

    public FilterType analyze(String text) {
        if (text == null || text == "") {
            return FilterType.GOOD;
        }
        if (text.length() > maxLength) {
            return FilterType.TOO_LONG;
        }
        return FilterType.GOOD;
    }
}
