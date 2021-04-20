package ru.mail.polis.homework.analyzer;

public class TooLongAnalyzer implements TextAnalyzer {
    private final long MAX_LENGHT;
    private final int PRIORITY;

    public TooLongAnalyzer(long maxLenght, int priority) {
        this.MAX_LENGHT = maxLenght;
        this.PRIORITY = priority;
    }

    @Override
    public int getPriority() {
        return PRIORITY;
    }

    @Override
    public boolean isFailed(String str) {
        return str == null || str.length() > MAX_LENGHT;
    }

    @Override
    public FilterType getFilterType() {
        return FilterType.TOO_LONG;
    }
}
