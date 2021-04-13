package ru.mail.polis.homework.analyzer;

public class TooLongAnalyzer implements TextAnalyzer {
    private static final int priority = 2;
    private static final FilterType filterType = FilterType.TOO_LONG;

    private final long maxLength;

    public TooLongAnalyzer(long maxLength) {
        this.maxLength = maxLength;
    }

    @Override
    public int priority() {
        return priority;
    }

    @Override
    public FilterType filterType() {
        return filterType;
    }

    @Override
    public boolean analyze(String text) {
        if (text == null || text.isEmpty()) {
            return false;
        }

        return text.length() > maxLength;
    }
}
