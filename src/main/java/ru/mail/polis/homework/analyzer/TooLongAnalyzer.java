package ru.mail.polis.homework.analyzer;

public class TooLongAnalyzer implements TextAnalyzer {
    private final long LENGTH;

    public TooLongAnalyzer(long maxLength) {
        this.LENGTH = maxLength;
    }


    @Override
    public FilterType analyze(String text) {
        if (text.length() >= LENGTH) {
            return FilterType.TOO_LONG;
        }
        return FilterType.GOOD;
    }


    public FilterType type() {
        return FilterType.TOO_LONG;
    }

}
