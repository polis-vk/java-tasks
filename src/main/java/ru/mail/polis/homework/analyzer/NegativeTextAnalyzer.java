package ru.mail.polis.homework.analyzer;

public class NegativeTextAnalyzer implements TextAnalyzer {
    private static final String[] negativeEmotions = new String[]{"=(", ":(", ":|"};
    private static final byte priority = 3;

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
        return FilterType.NEGATIVE_TEXT;
    }

    @Override
    public byte getPriority() {
        return priority;
    }
}
