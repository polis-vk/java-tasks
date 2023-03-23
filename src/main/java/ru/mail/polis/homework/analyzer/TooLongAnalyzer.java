package ru.mail.polis.homework.analyzer;

public class TooLongAnalyzer implements TextAnalyzer {
    long maxLength;
    static final FilterType analyzerType = FilterType.TOO_LONG;

    public TooLongAnalyzer(long maxLength) {
        this.maxLength = maxLength;
    }

    @Override
    public boolean detected(String expression) {
        return expression.length() > maxLength;
    }

    @Override
    public FilterType getFilterType() {
        return analyzerType;
    }
}