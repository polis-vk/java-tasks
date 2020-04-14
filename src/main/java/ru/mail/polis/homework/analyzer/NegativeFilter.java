package ru.mail.polis.homework.analyzer;

public class NegativeFilter implements TextAnalyzer {
    final String[] EMOTIONS = {"=(", ":(", ":|"};

    @Override
    public boolean filterIsPassed(String text) {
        for (String negativeEmotion : EMOTIONS) {
            if (text.contains(negativeEmotion)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public FilterType getFilterType() {
        return FilterType.NEGATIVE_TEXT;
    }
}
