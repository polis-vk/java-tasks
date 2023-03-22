package ru.mail.polis.homework.analyzer;

public class NegativeTextAnalyzer implements TextAnalyzer {
    private static final String[] negativeEmotions = new String[]{"=(", ":(", ":|"};
    private static final FilterType filterType = FilterType.NEGATIVE_TEXT;

    @Override
    public boolean isTextCorrect(String text) {
        for (String emotion : negativeEmotions) {
            if (text.contains(emotion)) {
                return false;
            }
        }
        return true;
    }

    @Override
    public FilterType getFilterType() {
        return filterType;
    }
}
