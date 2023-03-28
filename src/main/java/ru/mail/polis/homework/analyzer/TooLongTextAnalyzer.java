package ru.mail.polis.homework.analyzer;

public class TooLongTextAnalyzer implements TextAnalyzer {

    private final long maxStringLength;
    private final byte priority = 1;

    public TooLongTextAnalyzer(long maxStringLength) {
        this.maxStringLength = maxStringLength;
    }

    @Override
    public byte getPriority() {
        return priority;
    }

    @Override
    public FilterType analyze(String text) {
        if (text.length() > maxStringLength) {
            return FilterType.TOO_LONG;
        }
        return FilterType.GOOD;
    }
}
