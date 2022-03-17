package ru.mail.polis.homework.analyzer;

public abstract class BadWordsAnalyzer implements TextAnalyzer {
    protected String[] wordList;
    protected FilterType filterType;

    @Override
    public FilterType analyze(String text) {
        for (String word : wordList) {
            if (text.contains(word)) {
                return filterType;
            }
        }
        return FilterType.GOOD;
    }

    @Override
    public FilterType getAnalyzerType() {
        return filterType;
    }
}
