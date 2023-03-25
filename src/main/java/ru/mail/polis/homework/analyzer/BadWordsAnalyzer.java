package ru.mail.polis.homework.analyzer;

public class BadWordsAnalyzer implements TextAnalyzer {
    private final FilterType type = FilterType.CUSTOM;
    private final String[] badWords;

    BadWordsAnalyzer(String[] badWords){
        this.badWords = badWords;
    }

    @Override
    public boolean textAnalysis(String str) {
        for (String subStr : badWords) {
            if (str.contains(subStr)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public FilterType getType() {
        return type;
    }
}
