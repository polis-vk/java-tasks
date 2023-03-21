package ru.mail.polis.homework.analyzer;

public class NegativeTextAnalyser implements TextAnalyzer {
    private final int SIGNIFICANCE = 3;

    @Override
    public int getSignificance() {
        return SIGNIFICANCE;
    }

    @Override
    public FilterType analyze(String text) {
        for (int i = 1; i < text.length(); i++) {
            if (text.charAt(i - 1) == '=' && text.charAt(i) == '(') {
                return FilterType.NEGATIVE_TEXT;
            }
            if (text.charAt(i - 1) == ':' && (text.charAt(i) == '(' || text.charAt(i) == '|')) {
                return FilterType.NEGATIVE_TEXT;
            }
        }
        return FilterType.GOOD;
    }
}
