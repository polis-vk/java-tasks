package ru.mail.polis.homework.analyzer;

public class SpamAnalyzer implements TextAnalyzer {
    private final String[] restrictedWords;

    public SpamAnalyzer(String[] restrictedWords) {
        this.restrictedWords = restrictedWords;
    }

    @Override
    public FilterType getType() {
        return FilterType.SPAM;
    }

    @Override
    public boolean makeAnalysis(String text) {
        for (String badWord : restrictedWords) {
            if (text.contains(badWord)) {
                return false;
            }
        }
        return true;
    }
}
