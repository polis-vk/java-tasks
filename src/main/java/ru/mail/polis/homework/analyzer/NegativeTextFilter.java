package ru.mail.polis.homework.analyzer;

public class NegativeTextFilter extends Analyzing implements TextAnalyzer {

    private static final String[] NEGATIVE_SMILES = {"=(", ":(", ":|"};


    @Override
    public FilterType analyze(String text) {
        return analyzing(text, NEGATIVE_SMILES, FilterType.NEGATIVE_TEXT);
    }
}
