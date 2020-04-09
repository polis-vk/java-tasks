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
        return FindInText.find(spamWords, str, typeFilter);
    }
}
