package ru.mail.polis.homework.analyzer;

public class SpamTextFiler implements TextAnalyzer {
    private final static FilterType TYPE_FILTER = FilterType.SPAM;
    private final String[] spamWords;

    public SpamTextFiler(String[] spamWords) {
        this.spamWords = spamWords;
    }

    @Override
    public FilterType getFilterType() {
        return FilterType.SPAM;
    }

    @Override
    public FilterType analyze(String str) {
        return findInText(str, TYPE_FILTER);
    }

    public FilterType findInText( String str, FilterType neededType) {
        for (String item : spamWords) {
            if (str.contains(item)) {
                return neededType;
            }
        }
        return FilterType.GOOD;
    }
}