package ru.mail.polis.homework.analyzer;

public class SpamAnalyzer implements TextAnalyzer {
    private final String[] spam;
    private static final int PRIORITY = 1;

    public SpamAnalyzer(String[] spam) {
        this.spam = spam;
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

    @Override
    public int getPriority() {
        return PRIORITY;
    }
}
