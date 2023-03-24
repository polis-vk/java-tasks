package ru.mail.polis.homework.analyzer;

public class NegativeTextAnalyzer implements TextAnalyzer {
    final String[] negative = new String[]{"=(", ":(", ":|"};
    final static char priority = 2;

    @Override
    public FilterType filtering(String text) {
        for (String negativeSymbol : negative) {
            if (text.contains(negativeSymbol)) {
                return FilterType.NEGATIVE_TEXT;
            }
        }
        return FilterType.GOOD;
    }

    @Override
    public char getPriority() {
        return NegativeTextAnalyzer.priority;
    }
}
