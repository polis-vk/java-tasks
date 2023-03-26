package ru.mail.polis.homework.analyzer;

public class TooLongAnalyzer implements TextAnalyzer {
    private final long MAX_LENGTH;

    TooLongAnalyzer(long maxLength) {
        MAX_LENGTH = maxLength;
    }

    public FilterType analyze(String text) {
        if (text.length() > MAX_LENGTH) {
            return FilterType.TOO_LONG;
        }
        return FilterType.GOOD;
    }

    @Override
    public int getPriority() {
        return FilterType.TOO_LONG.getPriority();
    }
}
