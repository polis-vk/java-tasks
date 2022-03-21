package ru.mail.polis.homework.analyzer;

public class SpamAnalyzer implements TextAnalyzer {
    private String[] banWords;

    public SpamAnalyzer(String[] spam) {
        this.banWords = spam;
    }

    @Override
    public FilterType analyze(String comment) {
        for (String word : banWords) {
            if (comment.contains(word)) {
                return FilterType.SPAM;
            }
        }
        return FilterType.GOOD;
    }
}
