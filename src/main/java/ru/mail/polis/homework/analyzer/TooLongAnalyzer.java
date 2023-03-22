package ru.mail.polis.homework.analyzer;

public class TooLongAnalyzer implements TextAnalyzer {
    private static final int priority = 2;
    private static final FilterType filterType = FilterType.TOO_LONG;
    private final long length;

    TooLongAnalyzer(long length) {
        this.length = length;
    }


    @Override
    public int getPriority() {
        return priority;
    }

    @Override
    public FilterType getFilterType() {
        return filterType;
    }

    @Override
    public boolean analyze(String commentary) {
        return commentary.length() <= length;
    }
}
