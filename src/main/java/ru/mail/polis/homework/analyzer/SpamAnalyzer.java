package ru.mail.polis.homework.analyzer;

public class SpamAnalyzer implements TextAnalyzer {
    private String[] SPAM;

    public SpamAnalyzer(String[] spam) {
        this.SPAM = spam;
    }

    @Override
    public FilterType analyze(String text) {
        for (String spamWord : SPAM) {
            if (text.contains(spamWord)) {
                return FilterType.SPAM;
            }
        }
        return FilterType.GOOD;
    }
}
