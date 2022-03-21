package ru.mail.polis.homework.analyzer;

public class SpamAnalyzer implements TextAnalyzer {

    private final int priority;
    private final String[] spam;

    public SpamAnalyzer(String[] spam) {
        this.spam = spam;
        this.priority = FilterType.SPAM.getPriority();
    }

    @Override
    public int getPriority() {
        return priority;
    }

    @Override
    public FilterType analyze(String text) {
        if (text == null) {
            return FilterType.GOOD;
        }

        for (String spamWord : spam) {
            if (text.contains(spamWord)) {
                return FilterType.SPAM;
            }
        }

        return FilterType.GOOD;
    }
}
