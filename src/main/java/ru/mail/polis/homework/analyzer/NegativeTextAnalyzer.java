package ru.mail.polis.homework.analyzer;

public class NegativeTextAnalyzer implements TextAnalyzer {
    private final String[] badSmiles = new String[]{"=(", ":(", ":|"};

    @Override
    public FilterType analyzeText(String text) {
        for (String badSmile : badSmiles) {
            if (text.contains(badSmile)) {
                return FilterType.NEGATIVE_TEXT;
            }
        }
        return FilterType.GOOD;
    }

    @Override
    public FilterType getFilterType() {
        return FilterType.NEGATIVE_TEXT;
    }
}
