package ru.mail.polis.homework.analyzer;

public class NegativeTextAnalyzer extends ContentChecker implements TextAnalyzer {
    private static final FilterType type = FilterType.NEGATIVE_TEXT;
    private static final String[] negativeSmiles = new String[]{"=(", ":(", ":|"};

    @Override
    public FilterType filtering(String text) {
        return textContainWord(text, negativeSmiles) ? FilterType.NEGATIVE_TEXT : FilterType.GOOD;
    }

    @Override
    public FilterType getFilterType() {
        return type;
    }
}
