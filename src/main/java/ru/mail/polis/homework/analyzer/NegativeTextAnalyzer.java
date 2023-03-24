package ru.mail.polis.homework.analyzer;

public class NegativeTextAnalyzer implements TextAnalyzer {
    private static final String[] negativeSmiles = new String[]{"=(", ":(", ":|"};

    @Override
    public FilterType filtering(String text) {
        for (String negativeSmile : negativeSmiles) {
            if (text.contains(negativeSmile)) {
                return FilterType.NEGATIVE_TEXT;
            }
        }
        return FilterType.GOOD;
    }

    @Override
    public FilterType getReturnType() {
        return FilterType.NEGATIVE_TEXT;
    }
}
