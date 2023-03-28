package ru.mail.polis.homework.analyzer;

public class NegativeTextAnalyzer extends SpamAnalyzer implements TextAnalyzer {
    private static final String[] BAD_SMILES = {"=(", ":(", ":|"};

    public NegativeTextAnalyzer() {
        super(BAD_SMILES);
    }

    @Override
    public FilterType getFilterType() {
        return FilterType.NEGATIVE_TEXT;
    }
}
