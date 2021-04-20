package ru.mail.polis.homework.analyzer;

public class SpamAnalyzer implements  TextAnalyzer{
    private final String[] SPAM;
    private final int PRIORITY;

    public SpamAnalyzer(String[] spam, int priority) {
        this.SPAM = spam;
        this.PRIORITY = priority;
    }

    @Override
    public int getPriority() {
        return PRIORITY;
    }

    @Override
    public boolean isFailed(String str) {
        return str == null || TextAnalyzer.contains(str, SPAM);
    }

    @Override
    public FilterType getFilterType() {
        return FilterType.SPAM;
    }
}
