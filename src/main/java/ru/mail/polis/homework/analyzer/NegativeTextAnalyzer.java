package ru.mail.polis.homework.analyzer;

public class NegativeTextAnalyzer implements TextAnalyzer {
    private static final String[] negativeEmotions = new String[]{"=(", ":(", ":|"};
    private static final FilterType filterType = FilterType.NEGATIVE_TEXT;

    @Override
    public boolean isTextCorrect(String text) {
        return TextAnalyzer.find(text, negativeEmotions);
    }

    @Override
    public FilterType getFilterType() {
        return filterType;
    }
}
