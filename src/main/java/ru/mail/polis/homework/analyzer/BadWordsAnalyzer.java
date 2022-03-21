package ru.mail.polis.homework.analyzer;

public abstract class BadWordsAnalyzer implements TextAnalyzer {

    protected String[] badWords;
    protected FilterType filterType;

    @Override
    public boolean analyze(String text) {
        for (String negativeWord : badWords) {
            if (text.contains(negativeWord)) {
                return true;
            }
        }

        return false;
    }

    @Override
    public FilterType getType() {
        return filterType;
    }
}
