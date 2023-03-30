package ru.mail.polis.homework.analyzer;

public class TooManyWordsAnalyzer implements TextAnalyzer {
    private int maxWords;

    TooManyWordsAnalyzer(int maxWords) {
        this.maxWords = maxWords;
    }

    @Override
    public FilterType analyzeText(String text) {
        if (text.split(" +").length > maxWords) {
            return FilterType.TOO_MANY_WORDS;
        }
        return FilterType.GOOD;
    }

    @Override
    public FilterType getFilterType() {
        return FilterType.TOO_MANY_WORDS;
    }
}
