package ru.mail.polis.homework.analyzer;

public class CustomAnalyzer implements TextAnalyzer{
    private final int PRIORITY;

    public CustomAnalyzer(int priority) {
        this.PRIORITY = priority;
    }

    @Override
    public int getPriority() {
        return PRIORITY;
    }

    @Override
    public boolean isFailed(String str) {
        return str == null || str.equals(str.toUpperCase());
    }

    @Override
    public FilterType getFilterType() {
        return FilterType.CUSTOM;
    }
}
