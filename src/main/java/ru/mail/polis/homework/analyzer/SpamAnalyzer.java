package ru.mail.polis.homework.analyzer;

public class SpamAnalyzer implements TextAnalyzer {
    String[] spamWords;
    static final int priority = 1;
    static final FilterType analyzerType = FilterType.SPAM;

    public SpamAnalyzer(String[] spam) {
        spamWords = spam;
    }

    @Override
    public boolean detected(String expression) {
        for (String word : spamWords) {
            if (expression.contains(word)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public int getPriority() {
        return priority;
    }

    @Override
    public FilterType getFilterType() {
        return analyzerType;
    }
}
