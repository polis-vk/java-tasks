package ru.mail.polis.homework.analyzer;

public class SpamAnalyzer implements TextAnalyzer {
    private String[] badWords;

    SpamAnalyzer(String[] badWords) {
        this.badWords = badWords;
    }

    @Override
    public FilterType analyzeText(String text) {
        FilterType resultType = FilterType.GOOD;
        if (text == null) {
            return resultType;
        }
        for (String badWord :
                badWords) {
            if (text.contains(badWord)) {
                return FilterType.SPAM;
            }
        }
        return resultType;
    }

    @Override
    public FilterType getFilterType() {
        return FilterType.SPAM;
    }
}
