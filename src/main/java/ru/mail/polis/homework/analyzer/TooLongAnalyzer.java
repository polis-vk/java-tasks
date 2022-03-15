package ru.mail.polis.homework.analyzer;

public class TooLongAnalyzer implements TextAnalyzer {
    private final long maxLength;
    private final static FilterType FILTER_TYPE = FilterType.TOO_LONG;

    public TooLongAnalyzer(long maxLength) {
        this.maxLength = maxLength;
    }

    @Override
    public FilterType analyze(String text) {
        if (text.length() > maxLength) {
            return FILTER_TYPE;
        }
        return FilterType.GOOD;
    }

    @Override
    public int getPriority() {
        return FILTER_TYPE.getPriority();
    }
}
