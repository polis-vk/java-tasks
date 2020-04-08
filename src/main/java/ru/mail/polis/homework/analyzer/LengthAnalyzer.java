package ru.mail.polis.homework.analyzer;

public class LengthAnalyzer implements TextAnalyzer {
    private long maxLength;
    private Integer priority;

    public LengthAnalyzer(long maxLength) {
        this.maxLength = maxLength;
        priority = FilterType.TOO_LONG.ordinal();
    }

    public FilterType analysis(String text) {
        if (text.length() > maxLength) {
            return FilterType.TOO_LONG;
        }
        return FilterType.GOOD;
    }

    public Integer getPriority() {
        return priority;
    }
}
