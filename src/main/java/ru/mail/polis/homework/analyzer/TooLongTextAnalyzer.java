package ru.mail.polis.homework.analyzer;

public class TooLongTextAnalyzer implements TextAnalyzer {

    private final long maxLength;

    public TooLongTextAnalyzer(long maxLength) {
        this.maxLength = maxLength;
    }

    @Override
    public boolean analyze(String text) {
        return text.length() > maxLength;
    }

    @Override
    public FilterType getType() {
        return FilterType.TOO_LONG;
    }
}