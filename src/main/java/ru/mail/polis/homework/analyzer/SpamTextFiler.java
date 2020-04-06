package ru.mail.polis.homework.analyzer;

public class SpamTextFiler implements TextAnalyzer {
    private FilterType typePriority = FilterType.SPAM;
    private String[] spamWords;

    public SpamTextFiler(String[] spamWords) {
        this.spamWords = spamWords;
    }

    @Override
    public FilterType getTypePriority() {
        return FilterType.SPAM;
    }

    @Override
    public FilterType getTypeOfFilter(String str) {
        return FindInText.find(spamWords, str, typePriority);
    }
}
