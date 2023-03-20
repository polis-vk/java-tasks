package ru.mail.polis.homework.analyzer;

public class TooLongAnalyzer implements TextAnalyzer {
    long maxLength;
    static final int priority = 2;
    static final FilterType analyzerType = FilterType.TOO_LONG;

    public TooLongAnalyzer(long limit) {
        maxLength = limit;
    }

    @Override
    public boolean detected(String expression) {
        return expression.length() > maxLength;
    }

    @Override
    public int getPriority() {
        return priority;
    }

    @Override
    public FilterType getFilterType() {
        return analyzerType;
    }
}