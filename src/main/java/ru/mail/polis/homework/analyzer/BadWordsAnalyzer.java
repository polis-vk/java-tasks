package ru.mail.polis.homework.analyzer;

public class BadWordsAnalyzer implements TextAnalyzer {
    private final String[] badWords;
    private final FilterType filterType;

    public BadWordsAnalyzer(String[] spam) {
        badWords = spam;
        filterType = FilterType.SPAM;
    }

    public BadWordsAnalyzer() {
        badWords = new String[]{"=(", ":(", ":|"};
        filterType = FilterType.NEGATIVE_TEXT;
    }

    @Override
    public FilterType getFilterType() {
        return filterType;
    }

    @Override
    public FilterType analyze(String text) {
        for (String word : badWords) {
            if (text.contains(word)) {
                return filterType;
            }
        }
        return FilterType.GOOD;
    }
}