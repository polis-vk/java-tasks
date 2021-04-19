package ru.mail.polis.homework.analyzer;

public class NegativeFilter implements TextAnalyzer {
    private final String[] negativeChars = {"=(", ":(", ":|"};

    @Override
    public FilterType filterType() {
        return FilterType.NEGATIVE_TEXT;
    }

    @Override
    public boolean analyzeText(String text) {
        return AnalyzeText.analyzeText(text, negativeChars);
    }
}
