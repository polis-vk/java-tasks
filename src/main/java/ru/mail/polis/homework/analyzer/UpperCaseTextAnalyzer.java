package ru.mail.polis.homework.analyzer;

public class UpperCaseTextAnalyzer implements TextAnalyzer {
    private static final int priority = 3;
    private static final FilterType filterType = FilterType.UPPER_CASE_TEXT;

    public UpperCaseTextAnalyzer() {
    }

    @Override
    public int getPriority() {
        return priority;
    }

    @Override
    public FilterType getFilterType() {
        return filterType;
    }

    @Override
    public boolean isCorrect(String text) {
        for (int i = 0; i < text.length(); i++) {
            if (Character.isUpperCase(text.charAt(i))) {
                return false;
            }
        }
        return true;
    }
}
