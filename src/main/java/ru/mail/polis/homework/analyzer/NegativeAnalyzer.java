package ru.mail.polis.homework.analyzer;

public class NegativeAnalyzer implements TextAnalyzer {

    private static final String[] ILLEGAL_SYMBOLS = {"=(", ":(", ":|"};

    @Override
    public boolean problemDetected(String text) {
        return textContainsSthFromArray(text, ILLEGAL_SYMBOLS);
    }

    @Override
    public FilterType getFilterType() {
        return FilterType.NEGATIVE_TEXT;
    }

    public boolean textContainsSthFromArray(String text, String[] array){
        for (String s : array) {
            if (text.contains(s)) {
                return true;
            }
        }
        return false;
    }
}