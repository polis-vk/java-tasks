package ru.mail.polis.homework.analyzer;
public class NegativeAnalyzer
        extends MatchSearcher
        implements TextAnalyzer {
    private static final String[] ILLEGAL_SYMBOLS = {"=(", ":(", ":|"};
    @Override
    public boolean problemDetected(String text) {
        return textContainsSthFromArray(text, ILLEGAL_SYMBOLS);
    }
    @Override
    public FilterType getFilterType() {
        return FilterType.NEGATIVE_TEXT;
    }
}