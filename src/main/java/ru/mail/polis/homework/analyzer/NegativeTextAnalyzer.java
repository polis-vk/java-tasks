package ru.mail.polis.homework.analyzer;

public class NegativeTextAnalyzer implements TextAnalyzer {
    private String[] negativeSymbols = { ":(", ":|", "=(" };

    @Override
    public FilterType analyze(String text) {
        for (String negativeSymbol : negativeSymbols) {
            if (text.contains(negativeSymbol)) {
                return FilterType.NEGATIVE_TEXT;
            }
        }

        return FilterType.GOOD;
    }
}
