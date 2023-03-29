package ru.mail.polis.homework.analyzer;

public class NegativeTextAnalyzer implements TextAnalyzer {
    private final FilterType type = FilterType.NEGATIVE_TEXT;
    private final String[] sadSmiles = {"=(", ":(", ":|"};

    @Override
    public boolean filterWorked(String str) {
        for (String elem : sadSmiles) {
            if (str.contains(elem)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public FilterType getType() {
        return type;
    }
}
