package ru.mail.polis.homework.analyzer;

public abstract class BadWordsAnalyzer implements TextAnalyzer {
    protected String[] wordList;
    protected FilterType filterType;

    @Override
    public boolean analyze(String text) {
        for (String word : wordList) {
            if (text.contains(word)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public FilterType getAnalyzerType() {
        return filterType;
    }
}
