package ru.mail.polis.homework.analyzer;

public class IsTooLong implements TextAnalyzer {

    long textLength;
    public IsTooLong(long maxLength) {
        textLength = maxLength;
    }

    public FilterType analyze(String arg) {
        if (arg.length() > textLength) {
            return FilterType.TOO_LONG;
        }
        return FilterType.GOOD;
    }

    private int priority = 1;
    public int getPriority() {
        return priority;
    }
}
