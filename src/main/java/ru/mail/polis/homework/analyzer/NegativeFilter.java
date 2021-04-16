package ru.mail.polis.homework.analyzer;

public class NegativeFilter implements TextAnalyzer {
    private static final FilterType type = FilterType.NEGATIVE_TEXT;
    private static final String[] emotions = {"=(", ":(", ":|"};

    @Override
    public FilterType getType() {
        return type;
    }

    @Override
    public boolean isNotCorrect(String text) {
        if (text == null || text.isEmpty()) {
            return false;
        }

        for (String emotion : emotions) {
            if (text.contains(emotion)) {
                return true;
            }
        }
        return false;
    }
}
