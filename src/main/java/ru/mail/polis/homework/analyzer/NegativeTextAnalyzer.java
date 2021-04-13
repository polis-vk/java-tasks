package ru.mail.polis.homework.analyzer;

public class NegativeTextAnalyzer implements TextAnalyzer {
    private static final int priority = 3;
    private static final FilterType filterType = FilterType.NEGATIVE_TEXT;
    private static final String[] negativeText = {"=(", ":(", ":|"};

    @Override
    public int priority() {
        return priority;
    }

    @Override
    public FilterType filterType() {
        return filterType;
    }

    @Override
    public boolean analyze(String text) {
        for (String negativeWord : negativeText) {
            if (text.contains(negativeWord)) {
                return true;
            }
        }
        return false;
    }
}
