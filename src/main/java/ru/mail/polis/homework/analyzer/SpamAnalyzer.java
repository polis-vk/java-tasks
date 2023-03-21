package ru.mail.polis.homework.analyzer;

public class SpamAnalyzer
        extends MatchSearcher
        implements TextAnalyzer {
    private final String[] spam;

    public SpamAnalyzer(String[] spam) {
        this.spam = spam.clone();
    }

    @Override
    public boolean problemDetected(String text) {
        return textContainsSthFromArray(text, spam);
    }

    @Override
    public FilterType getFilterType() {
        return FilterType.SPAM;
    }
}