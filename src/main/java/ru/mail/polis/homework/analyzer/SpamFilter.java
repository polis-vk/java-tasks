package ru.mail.polis.homework.analyzer;

public class SpamFilter implements TextAnalyzer {
    private final String[] spam;

    public SpamFilter(String[] spam) {
        this.spam = spam;
    }

    @Override
    public FilterType filterType() {
        return FilterType.SPAM;
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
