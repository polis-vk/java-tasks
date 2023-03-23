package ru.mail.polis.homework.analyzer;

public class LongFilterAnalyzer implements TextAnalyzer {
    private final long maxLength;

    LongFilterAnalyzer(long maxLength) {
        this.maxLength = maxLength;
    }

    @Override
    public int getTypeOrder() {
        return FilterType.TOO_LONG.order;
    }

    @Override
    public FilterType analyze(String text) {
        return text.length() > maxLength ? FilterType.TOO_LONG : FilterType.GOOD;
    }
}
