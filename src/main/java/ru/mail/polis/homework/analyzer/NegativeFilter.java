package ru.mail.polis.homework.analyzer;

public class NegativeFilter implements TextAnalyzer {
    final int PRIORITY = 3;
    public static final FilterType typeOfFilter = FilterType.NEGATIVE_TEXT;
    private static final String[] negativeWords = {"=(", ":(", ":|"};

    @Override
    public int getPriority() {
        return PRIORITY;
    }

    @Override
    public FilterType getType() {
        return typeOfFilter;
    }

    @Override
    public boolean analyze(String text) {
        if (text == null) {
            return false;
        }
        for (String badWord : negativeWords) {
            if (text.contains(badWord)) {
                return true;
            }
        }
        return false;
    }
}
