package ru.mail.polis.homework.analyzer;

public class SpamTextFiler implements TextAnalyzer {
    private final static FilterType typeFilter = FilterType.SPAM;
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
        return findInText(spamWords, str, typeFilter);
    }

    public FilterType findInText(String[] templateArray, String str, FilterType neededType) {
        for (String item : templateArray) {
            if (str.contains(item)) {
                return neededType;
            }
        }
        return FilterType.GOOD;
    }
}