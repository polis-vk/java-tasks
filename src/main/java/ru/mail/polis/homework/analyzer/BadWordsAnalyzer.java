package ru.mail.polis.homework.analyzer;

public class BadWordsAnalyzer implements TextAnalyzer {
    protected String[] badWords;
    protected FilterType filterType;

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