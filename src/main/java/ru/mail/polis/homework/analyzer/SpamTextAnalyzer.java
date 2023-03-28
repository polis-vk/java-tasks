package ru.mail.polis.homework.analyzer;

public class SpamTextAnalyzer implements TextAnalyzer {
    private final String[] spam;

    public SpamTextAnalyzer(String[] spam) {
        this.spam = spam;
    }

    @Override
    public boolean analyzeFilterStatus(String text) {
        for (String word : spam) {
            if (text.contains(word)) {
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
