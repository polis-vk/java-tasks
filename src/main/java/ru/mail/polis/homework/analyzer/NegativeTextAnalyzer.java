package ru.mail.polis.homework.analyzer;

public class NegativeTextAnalyzer implements TextAnalyzer {
    private static final String[] NEGATIVE_EMOTIONS = new String[]{"=(", ":(", ":|"};
    private static final FilterType FILTER_TYPE = FilterType.NEGATIVE_TEXT;

    @Override
    public boolean isTextCorrect(String text) {
        return TextAnalyzer.find(text, NEGATIVE_EMOTIONS);
    }

    @Override
    public FilterType getFilterType() {
        return FILTER_TYPE;
    }
}
