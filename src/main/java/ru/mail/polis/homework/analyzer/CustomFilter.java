package ru.mail.polis.homework.analyzer;

public class CustomFilter implements TextAnalyzer {
    @Override
    public FilterType filterType() {
        return FilterType.CUSTOM;
    }

    @Override
    public boolean analyzeText(String text) {
        final String[] isDigit = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9"};
        for (String num : isDigit) {
            if (text.contains(num)) {
                return true;
            }
        }
        return false;
    }
}
