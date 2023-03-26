package ru.mail.polis.homework.analyzer;

public class SpamAnalyzer implements TextAnalyzer {
    private final String[] spam;

    public SpamAnalyzer(String[] spam) {
        this.spam = spam;
    }

    @Override
    public boolean filterSuccess(String text) {
        for (String badWord : spam) {
            if (text.contains(badWord)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public FilterType filterType() {
        return FilterType.SPAM;
    }
}
