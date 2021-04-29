package ru.mail.polis.homework.analyzer;

public class TooLongAnalyzer implements TextAnalyzer {

    private final long MAX_LENGTH;

    public TooLongAnalyzer(long maxLength) {
        this.MAX_LENGTH = maxLength;
    }

    @Override
    public FilterType analyze(String text) {
        if (text.length() > MAX_LENGTH) {
            return FilterType.TOO_LONG;
        }
        return FilterType.GOOD;
    }

    @Override
    public FilterType getType() {
        return FilterType.TOO_LONG;
    }

}
