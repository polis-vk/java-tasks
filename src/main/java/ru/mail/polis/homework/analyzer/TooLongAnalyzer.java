package ru.mail.polis.homework.analyzer;

public class TooLongAnalyzer implements TextAnalyzer {
    private final long MAX_LENGTH;
    private final int PRIORITY = 2;

    public TooLongAnalyzer(long max_length) {
        MAX_LENGTH = max_length;
    }

    @Override
    public boolean analyze(String str) {
        if (str.length() > this.MAX_LENGTH) {
            return true;
        }

        return false;
    }

    @Override
    public FilterType getType() {
        return FilterType.TOO_LONG;
    }

    @Override
    public int getPriority() {
        return this.PRIORITY;
    }

}
