package ru.mail.polis.homework.analyzer;

public class NegativeTextAnalyzer implements TextAnalyzer {
    private String[] NEGATIVE_TEXT = {"=(", ":(", ":|"};

    @Override
    public FilterType analyze(String text) {
        for (String negativeSymbol : NEGATIVE_TEXT) {
            if (text.contains(negativeSymbol)) {
                return FilterType.NEGATIVE_TEXT;
            }
        }
        return FilterType.GOOD;
    }
}



