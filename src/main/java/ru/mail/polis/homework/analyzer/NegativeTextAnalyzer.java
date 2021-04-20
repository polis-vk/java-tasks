package ru.mail.polis.homework.analyzer;

public class NegativeTextAnalyzer implements TextAnalyzer {
    private static final String[] NEGATIVE = { "=(", ":(", ":|" };
    private final int PRIORITY;

    public NegativeTextAnalyzer(int priority) {
        this.PRIORITY = priority;
    }

    @Override
    public int getPriority() {
        return PRIORITY;
    }

    @Override
    public FilterType getFilterType() {
        return FilterType.NEGATIVE_TEXT;
    }

    @Override
    public boolean isPassed(String str) {
        return str != null && !TextAnalyzer.contains(str, NEGATIVE);
    }
}
