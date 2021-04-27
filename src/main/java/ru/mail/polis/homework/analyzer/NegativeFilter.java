package ru.mail.polis.homework.analyzer;

public class NegativeFilter implements TextAnalyzer {
    private static final String[] negativeChars = {"=(", ":(", ":|"};

    @Override
    public FilterType filterType() {
        return FilterType.NEGATIVE_TEXT;
    }

    @Override
    public boolean analyzeText(String text) {
        return analyzeText(text, negativeChars);
    }

    public static boolean analyzeText(String text, String[] array) {
        for (String strangeWords : array) {
            if (text.contains(strangeWords)) {
                return true;
            }
        }
        return false;
    }
}
