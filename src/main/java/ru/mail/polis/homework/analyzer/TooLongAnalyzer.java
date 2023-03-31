package ru.mail.polis.homework.analyzer;

public class TooLongAnalyzer implements TextAnalyzer {
    private final long size;

    public TooLongAnalyzer(long size) {
        this.size = size;
    }

    @Override
    public FilterType getFilterType(String str) {
        return (str.length() > size) ? FilterType.TOO_LONG : FilterType.GOOD;
    }

    @Override
    public int getPriority() {
        return 2;
    }
}
