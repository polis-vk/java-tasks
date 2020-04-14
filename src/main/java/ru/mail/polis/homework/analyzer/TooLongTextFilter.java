package ru.mail.polis.homework.analyzer;

public class TooLongTextFilter implements TextAnalyzer {

    private final long maxLength;

    public TooLongTextFilter(long maxLength) {
        this.maxLength = maxLength;
    }

    @Override
    public FilterType analyze(String text) {
        if (text.length() > maxLength) {
            return FilterType.TOO_LONG;
        }
        return good;
    }
}
