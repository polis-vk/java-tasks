package ru.mail.polis.homework.analyzer;

public class TooLongAnalyzer implements TextAnalyzer {

    private final int priority;
    private final long maxLength;

    public TooLongAnalyzer(long maxLength) {
        this.maxLength = maxLength;
        this.priority = FilterType.TOO_LONG.getPriority();
    }

    @Override
    public int getPriority() {
        return priority;
    }

    @Override
    public FilterType analyze(String text) {
        if (text == null) {
            return FilterType.GOOD;
        }

        return text.length() <= maxLength ? FilterType.GOOD : FilterType.TOO_LONG;
    }
}
