package ru.mail.polis.homework.analyzer;

public class TextNegativeTextAnalyzer implements TextAnalyzer {

    private final String[] negativeMorphemes = {"=(", ":(", ":|"};

    @Override
    public boolean analyze(String text) {
        for (String s : negativeMorphemes) {
            if (text.contains(s)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public FilterType getType() {
        return FilterType.NEGATIVE_TEXT;
    }
}
