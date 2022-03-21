package ru.mail.polis.homework.analyzer;

public class TooLongAnalyzer implements TextAnalyzer {

    private long max_length;

    public TooLongAnalyzer(long maxLength) {
        this.max_length = maxLength;
    }

    @Override
    public FilterType getFilterType() {
        return FilterType.TOO_LONG;
    }

    @Override
    public boolean doAnalyze(String text) {
        return text.length() > max_length;
    }
}
