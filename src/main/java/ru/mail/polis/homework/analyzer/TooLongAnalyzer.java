package ru.mail.polis.homework.analyzer;

public class TooLongAnalyzer implements TextAnalyzer {
    private long maxLength;
    public TooLongAnalyzer(long maxLength) {
        this.maxLength = maxLength;
    }
    @Override
    public FilterType analyze(String text) {
        if (text == null) {
            return FilterType.GOOD;
        }
        if (text.length() <= maxLength) {
            return FilterType.GOOD;
        }
        return FilterType.TOO_LONG;
    }
}
