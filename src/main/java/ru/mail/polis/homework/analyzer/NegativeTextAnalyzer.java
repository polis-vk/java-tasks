package ru.mail.polis.homework.analyzer;

public class NegativeTextAnalyzer implements TextAnalyzer {
    private static final int priority = 2;
    private static final FilterType filterType = FilterType.NEGATIVE_TEXT;
    private String[] negativeWords;

    public NegativeTextAnalyzer(String[] negativeWords) {
        this.negativeWords = negativeWords;
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
        for (String word : negativeWords) {
            if (text.contains(word)) {
                return false;
            }
        }
        return true;
    }
}
