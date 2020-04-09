package ru.mail.polis.homework.analyzer;

public class NegativeTextAnalyzer implements TextAnalyzer {
    private String[] badSmiles = {"=(", ":(", ":|"};

    public boolean isTrue(String text) {
        for (String smile: badSmiles) {
            if (text.contains(smile)) {
                return true;
            }
        }
        return false;
    }

    public FilterType getFilterType() {
        return FilterType.NEGATIVE_TEXT;
    }
}
