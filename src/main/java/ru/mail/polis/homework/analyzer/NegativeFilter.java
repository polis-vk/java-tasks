package ru.mail.polis.homework.analyzer;

public class NegativeFilter implements TextAnalyzer {
    private static final byte priority = 3;
    private static final FilterType filterType = FilterType.NEGATIVE_TEXT;
    private static final String[] negative = {"=(", ":(", ":|"};

    @Override
    public byte priority() {
        return priority;
    }

    @Override
    public FilterType filterType() {
        return filterType;
    }

    @Override
    public boolean analyzeText(String text) {
        for (String negativeWord : negative) {
            if (text.contains(negativeWord)) {
                return true;
            }
        }
        return false;
    }
}
