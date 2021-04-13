package ru.mail.polis.homework.analyzer;

public class SpamAnalyzer implements TextAnalyzer {
    private static final int priority = 1;
    private static final FilterType filterType = FilterType.SPAM;

    private final String[] spam;


    public SpamAnalyzer(String[] spam) {
        this.spam = spam;
    }

    @Override
    public int priority() {
        return priority;
    }

    @Override
    public FilterType filterType() {
        return filterType;
    }

    @Override
    public boolean analyze(String text) {
        for (String spamWord : spam) {
            if (text.contains(spamWord)) {
                return true;
            }
        }
        return false;
    }
}
