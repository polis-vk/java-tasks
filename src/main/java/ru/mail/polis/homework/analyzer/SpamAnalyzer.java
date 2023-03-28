package ru.mail.polis.homework.analyzer;

public class SpamAnalyzer implements TextAnalyzer {
    private final String[] spamWords;
    private static final FilterType filterType = FilterType.SPAM;

    public SpamAnalyzer(String[] spamWords) {
        this.spamWords = spamWords;
    }

    @Override
    public boolean filterWorked(String inputString) {
        for (String word : spamWords) {
            if (inputString.contains(word)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public FilterType getFilterType() {
        return filterType;
    }
}
