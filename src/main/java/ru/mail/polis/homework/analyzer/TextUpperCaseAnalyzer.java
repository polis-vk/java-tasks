package ru.mail.polis.homework.analyzer;

public class TextUpperCaseAnalyzer implements TextAnalyzer {
    @Override
    public boolean analyzeText(String text) {
        for (int i = 0; i < text.length(); i++) {
            if (Character.isUpperCase(text.charAt(i))) {
                return true;
            }
        }
        return false;
    }

    @Override
    public FilterType getTypeOfFilter() {
        return FilterType.CUSTOM;
    }
}

