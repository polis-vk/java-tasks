package ru.mail.polis.homework.analyzer;

public class SpamFilter implements TextAnalyzer {
    private static final byte priority = 1;
    private static final FilterType filterType = FilterType.SPAM;
    private final String[] spam;

    public SpamFilter(String[] spam) {
        this.spam = spam;
    }

    @Override
    public byte priority() {
        return priority;
    }

    @Override
    public FilterType filterType() {
        return filterType;
    }

    @Override
    public boolean analyzeText(String text) {
        for (String spamWords : spam) {
            if (text.contains(spamWords)) {
                return true;
            }
        }
        return false;
    }
}
