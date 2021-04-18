package ru.mail.polis.homework.analyzer;

public class NegativeFilter implements TextAnalyzer {
    @Override
    public FilterType filterType() {
        return FilterType.NEGATIVE_TEXT;
    }

    @Override
    public boolean analyzeText(String text) {
        final String[] negativeChars = {"=(", ":(", ":|"};
        for (String negativeChar : negativeChars) {
            if (text.contains(negativeChar)) {
                return true;
            }
        }
        return false;
    }
}
