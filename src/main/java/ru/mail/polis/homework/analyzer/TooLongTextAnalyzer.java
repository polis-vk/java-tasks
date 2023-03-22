package ru.mail.polis.homework.analyzer;

public class TooLongTextAnalyzer implements TextAnalyzer {
    private static final int priority = 1;
    private static final FilterType filterType = FilterType.TOO_LONG;
    private long maxLength;

    public TooLongTextAnalyzer(long maxLength) {
        this.maxLength = maxLength;
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
    public boolean isCorrect(String text) {
        return text.length() <= maxLength;
    }
}
