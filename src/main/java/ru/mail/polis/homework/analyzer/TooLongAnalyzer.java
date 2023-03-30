package ru.mail.polis.homework.analyzer;

public class TooLongAnalyzer implements TextAnalyzer {
    private final long size;

    public TooLongAnalyzer(long size) {
        this.size = size;
    }

    @Override
    public boolean isBadText(String text) {
        return text.length() > size;
    }

    @Override
    public FilterType getTypeFilter() {
        return FilterType.TOO_LONG;
    }
}
