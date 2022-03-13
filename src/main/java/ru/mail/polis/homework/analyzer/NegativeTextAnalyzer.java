package ru.mail.polis.homework.analyzer;

public class NegativeTextAnalyzer implements TextAnalyzer {
    private final String[] negativeWords = new String[]{"=(", ":(", ":|"};
    private static final int PRIORITY = 3;

    @Override
    public FilterType analyze(String text) {
        for (String negativeWord : negativeWords) {
            if (text.contains(negativeWord)) {
                return FilterType.NEGATIVE_TEXT;
            }
        }
        return FilterType.GOOD;
    }

    @Override
    public int getPriority() {
        return PRIORITY;
    }
}
