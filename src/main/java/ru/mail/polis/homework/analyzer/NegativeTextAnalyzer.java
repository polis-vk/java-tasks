package ru.mail.polis.homework.analyzer;

public class NegativeTextAnalyzer implements TextAnalyzer, CheckWord {

    private final String[] SMILES = {"=(", ":(", ":|"};

    public boolean analyzer(String text) {
        if (text == null || text.isEmpty()) {
            return false;
        }
        return checkWord(text, SMILES);
    }

    public FilterType getType() {
        return FilterType.NEGATIVE_TEXT;
    }
}
