package ru.mail.polis.homework.analyzer;

public class SpamAnalyzer implements TextAnalyzer {
    private final String[] spam;

    public SpamAnalyzer(String[] spam) {
        this.spam = spam;
    }

    @Override
    final public int priority() {
        return filterType().getPriority();
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
