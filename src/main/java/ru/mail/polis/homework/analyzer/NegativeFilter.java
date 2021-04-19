package ru.mail.polis.homework.analyzer;

public class NegativeFilter implements TextAnalyzer {
    private static final String[] emotions = {"=(", ":(", ":|"};

    @Override
    public byte getPriority() {
        return 3;
    }

    @Override
    public FilterType getType() {
        return FilterType.NEGATIVE_TEXT;
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
