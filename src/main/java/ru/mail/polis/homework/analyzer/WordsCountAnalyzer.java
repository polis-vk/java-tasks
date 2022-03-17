package ru.mail.polis.homework.analyzer;

public class WordsCountAnalyzer implements TextAnalyzer {
    private final int minWordsCount;

    public WordsCountAnalyzer(int minWordsCount) {
        this.minWordsCount = minWordsCount;
    }

    @Override
    public FilterType getFilterType() {
        return FilterType.CUSTOM;
    }

    @Override
    public FilterType analyze(String text) {
        String onlyWords = text.replaceAll("[^0-9A-zА-я]", " ");
        onlyWords = onlyWords.replaceAll("\\s{2,}", " ");
        if (onlyWords.trim().isEmpty()) {
            return FilterType.CUSTOM;
        }
        String[] words = onlyWords.split(" ");
        return words.length < minWordsCount ? FilterType.CUSTOM : FilterType.GOOD;
    }
}