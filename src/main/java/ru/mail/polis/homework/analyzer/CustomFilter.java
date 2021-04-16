package ru.mail.polis.homework.analyzer;

public class CustomFilter implements TextAnalyzer {
    private static final byte priority = 4;
    private static final FilterType filterType = FilterType.CUSTOM;
    private static final String[] isDigit = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9"};

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
        for (String num : isDigit) {
            if (text.contains(num)) {
                return true;
            }
        }
        return false;
    }
}
