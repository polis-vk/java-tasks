package ru.mail.polis.homework.analyzer;

public class NegativeAnalyzer implements TextAnalyzer {
    private static final String[] ILLEGAL_SYMBOLS = {"=(", ":(", ":|"};

    @Override
    public boolean problemDetected(String text) {
        for (String s : ILLEGAL_SYMBOLS) {
            if (text.contains(s)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public FilterType getFilterType() {
        return FilterType.NEGATIVE_TEXT;
    }
}