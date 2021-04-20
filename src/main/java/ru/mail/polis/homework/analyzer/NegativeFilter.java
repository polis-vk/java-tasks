package ru.mail.polis.homework.analyzer;

public class NegativeFilter implements TextAnalyzer {
    private static final String[] EMOTIONS = {"=(", ":(", ":|"};

    @Override
    public FilterType getType() {
        return FilterType.NEGATIVE_TEXT;
    }

    @Override
    public boolean isNotCorrect(String text) {
        if (text == null) {
            return false;
        }

        for (String emotion : EMOTIONS) {
            if (text.contains(emotion)) {
                return true;
            }
        }
        return false;
    }
}
