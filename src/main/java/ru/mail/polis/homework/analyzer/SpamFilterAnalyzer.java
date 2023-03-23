package ru.mail.polis.homework.analyzer;

public class SpamFilterAnalyzer implements TextAnalyzer {
    private final String[] spam;

    SpamFilterAnalyzer(String[] spam) {
        this.spam = spam;
    }

    @Override
    public int getTypeOrder() {
        return FilterType.SPAM.order;
    }

    @Override
    public FilterType analyze(String text) {
        for (String spamWord : spam) {
            if (text.contains(spamWord)) {
                return FilterType.SPAM;
            }
        }
        return FilterType.GOOD;
    }

}
