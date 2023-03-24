package ru.mail.polis.homework.analyzer;

public class SpamAnalyzer implements TextAnalyzer {
    private final String[] spamWords;

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
    public FilterType getFilterType() {
        return FilterType.SPAM;
    }
}
