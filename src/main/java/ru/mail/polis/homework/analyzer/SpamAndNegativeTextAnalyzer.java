package ru.mail.polis.homework.analyzer;

public class SpamAndNegativeTextAnalyzer implements TextAnalyzer {

    private final String[] negativeWords;
    private final FilterType filterType;

    public SpamAndNegativeTextAnalyzer(String[] negativeWords, FilterType filterType) {
        this.negativeWords = negativeWords;
        this.filterType = filterType;
    }

    @Override
    public FilterType analyze(String text) {
        for (String negativeWord : negativeWords) {
            if (text.contains(negativeWord)) {
                return filterType;
            }
        }

        return FilterType.GOOD;
    }

    @Override
    public FilterType getPriority() {
        return filterType;
    }
}
