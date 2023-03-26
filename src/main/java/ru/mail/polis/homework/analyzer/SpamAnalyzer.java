package ru.mail.polis.homework.analyzer;

public class SpamAnalyzer implements TextAnalyzer {
    private final FilterType filterType;
    private final String[] badWords;

    public SpamAnalyzer(String[] badWords) {
        this.badWords = badWords;
        this.filterType = FilterType.SPAM;
    }

    @Override
    public FilterType getType() {
        return this.filterType;
    }

    @Override
    public FilterType makeAnalysis(String text) {
        for (String badWord : badWords) {
            if (text.contains(badWord)) {
                return this.filterType;
            }
        }
        return FilterType.GOOD;
    }

}
