package ru.mail.polis.homework.analyzer;

public class SpamTextAnalyzer implements TextAnalyzer {
    final String[] negativeWords;

    public SpamTextAnalyzer(String[] negativeWords) {
        this.negativeWords = negativeWords;
    }

    @Override
    public boolean analyzeText(String text) {
        for (String negativeWord : negativeWords) {
            if (text.contains(negativeWord)) {
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
