package ru.mail.polis.homework.analyzer;

public class CustomFilter implements TextAnalyzer {
    @Override
    public FilterType filterType() {
        return FilterType.CUSTOM;
    }

    @Override
    public boolean analyzeText(String text) {
        for (int i = 0; i < text.length(); i++) {
            if (Character.isDigit(text.charAt(i))) {
                return true;
            }
        }
        return false;
    }
}
