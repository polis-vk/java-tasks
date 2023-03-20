package ru.mail.polis.homework.analyzer;

public class TooLongAnalyzer implements TextAnalyzer {
    public TooLongAnalyzer(long maxLength) {
        maxLength_ = maxLength;
    }

    @Override
    public boolean detected(String expression) {
        return expression.length() > maxLength_;
    }

    @Override
    public int getPriority() {
        return priority_;
    }

    @Override
    public FilterType getFilterType() {
        return analyzerType_;
    }

    long maxLength_;
    static final int priority_ = 2;
    static final FilterType analyzerType_ = FilterType.TOO_LONG;
}