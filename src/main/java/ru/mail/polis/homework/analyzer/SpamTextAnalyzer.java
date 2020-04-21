package ru.mail.polis.homework.analyzer;

public class SpamTextAnalyzer implements TextAnalyzer {
    private String[] spamWords;

    public SpamTextAnalyzer(String[] spamWords) {
        this.spamWords = spamWords;
    }

    @Override
    public FilterType analyze(String text) {
        for (String spamWord : spamWords) {
            if (text.contains(spamWord)) {
                return FilterType.SPAM;
            }
        }

        return FilterType.GOOD;
    }
}

