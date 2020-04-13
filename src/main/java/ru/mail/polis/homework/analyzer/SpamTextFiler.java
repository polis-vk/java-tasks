package ru.mail.polis.homework.analyzer;

public class SpamTextFiler implements TextAnalyzer {
    private final static FilterType TYPE_FILTER = FilterType.SPAM;
    private final String[] spamWords;

    public SpamTextFiler(final String[] spamWords) {
        this.spamWords = spamWords;
    }

    @Override
    public FilterType getFilterType() {
        return TYPE_FILTER;
    }

    @Override
    public FilterType analyze(String str) {
        final FilterType neededType = getFilterType();
        for (String item : spamWords) {
            if (str.contains(item)) {
                return neededType;
            }
        }
        return FilterType.GOOD;
    }
}