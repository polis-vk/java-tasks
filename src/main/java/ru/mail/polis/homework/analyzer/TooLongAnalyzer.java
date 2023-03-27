package ru.mail.polis.homework.analyzer;

public class TooLongAnalyzer implements TextAnalyzer {
    private static final int PRIORITY = 2;
    private final long LENGTH;

    public TooLongAnalyzer(long length) {
        this.LENGTH = length;
    }


    @Override
    public int getPriority() {
        return PRIORITY;
    }

    @Override
    public FilterType getFilterType() {
        return FilterType.TOO_LONG;
    }

    @Override
    public boolean analyze(String commentary) {
        return commentary.length() <= LENGTH;
    }
}
