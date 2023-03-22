package ru.mail.polis.homework.analyzer;

public class SpamTextAnalyzer implements TextAnalyzer {
    private static final int priority = 0;
    private static final FilterType filterType = FilterType.SPAM;
    private String[] spam;

    public SpamTextAnalyzer(String[] spam) {
        this.spam = spam;
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
        for (String word : spam) {
            if (text.contains(word)) {
                return false;
            }
        }
        return true;
    }
}
