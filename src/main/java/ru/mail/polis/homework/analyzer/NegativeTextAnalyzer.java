package ru.mail.polis.homework.analyzer;

public class NegativeTextAnalyzer implements TextAnalyzer {
    private final FilterType type = FilterType.NEGATIVE_TEXT;
    private final String[] NegativeStrings = {"=(", ":(", ":|"};

    @Override
    public boolean resultResearch(String str) {
        for (String elem : NegativeStrings) {
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

