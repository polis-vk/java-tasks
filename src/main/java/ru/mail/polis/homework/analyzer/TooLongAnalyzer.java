package ru.mail.polis.homework.analyzer;

public class TooLongAnalyzer implements TextAnalyzer {

    private final long MAX_LENGTH;

    public TooLongAnalyzer(long maxLength) {
        this.MAX_LENGTH = maxLength;
    }

    @Override
    public FilterType getFilterType() {
        return FilterType.TOO_LONG;
    }

    @Override
    public boolean doAnalyze(String text) {
        return text.length() > this.MAX_LENGTH;
    }
}
