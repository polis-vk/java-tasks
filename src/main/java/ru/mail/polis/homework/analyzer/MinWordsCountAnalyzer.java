package ru.mail.polis.homework.analyzer;

public class MinWordsCountAnalyzer implements TextAnalyzer {
    private final int minWordsCount;

    public MinWordsCountAnalyzer(int minWordsCount) {
        this.minWordsCount = minWordsCount;
    }

    @Override
    public FilterType getFilterType() {
        return FilterType.CUSTOM;
    }

    @Override
    public boolean analyze(String text) {
        String onlyWords = text.replaceAll("[^0-9A-zА-я]", " ");
        onlyWords = onlyWords.replaceAll("\\s{2,}", " ");
        if (onlyWords.trim().isEmpty()) {
            return true;
        }
        String[] words = onlyWords.split(" ");
        return words.length < minWordsCount;
    }
}
