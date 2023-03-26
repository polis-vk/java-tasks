package ru.mail.polis.homework.analyzer;

import java.util.Arrays;

public class SpamAnalyzer
        extends MatchSearcher
        implements TextAnalyzer {
    private final String[] spam;

    public SpamAnalyzer(String[] spam) {
        this.spam = Arrays.copyOf(spam, spam.length);
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