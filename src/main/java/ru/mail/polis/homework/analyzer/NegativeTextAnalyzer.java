package ru.mail.polis.homework.analyzer;

public class NegativeTextAnalyzer implements TextAnalyzer {
    private final String[] badSmiles = new String[]{"=(", ":(", ":|"};

    @Override
    public FilterType analyzeText(String text) {
        FilterType resultType = FilterType.GOOD;
        if (text == null) {
            return resultType;
        }
        for (String badSmile :
                badSmiles) {
            if (text.contains(badSmile)) {
                return FilterType.NEGATIVE_TEXT;
            }
        }
        return resultType;
    }

    @Override
    public FilterType getFilterType() {
        return FilterType.NEGATIVE_TEXT;
    }
}
