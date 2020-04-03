package ru.mail.polis.homework.analyzer;

public class NegativeFilter implements TextAnalyzer {

    private String[] negative = {"=(", ":(", ":|"};

    @Override
    public FilterType analyze(String str) {
        for (String word : negative) {
            if (str.contains(word)) {
                return FilterType.NEGATIVE_TEXT;
            }
        }
        return null;
    }

    @Override
    public FilterType getPriority() {
        return FilterType.NEGATIVE_TEXT;
    }
}
