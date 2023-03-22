package ru.mail.polis.homework.analyzer;

public class TooLongAnalyzer implements TextAnalyzer {
    final private long maxLength;
    static final private FilterType filterType = FilterType.TOO_LONG;

    public TooLongAnalyzer(long maxLength) {
        this.maxLength = maxLength;
    }

    @Override
    public boolean filterWorked(String inputString) {
        return maxLength < inputString.length();
    }

    @Override
    public FilterType getFilterType() {
        return filterType;
    }
}
