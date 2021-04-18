package ru.mail.polis.homework.analyzer;

public class SpamAnalyzer implements TextAnalyzer {
    private final String[] spam;

    public SpamAnalyzer(String[] spam) {
        this.spam = spam;
    }

    @Override
    public int priority() {
        return 1;
    }

    @Override
    public FilterType filterType() {
        return FilterType.SPAM;
    }

    @Override
    public FilterType analyze(String text) {
        for (String spamItem : spam) {
            if (text.contains(spamItem)) {
                return filterType();
            }
        }
        return FilterType.GOOD;
    }
}
