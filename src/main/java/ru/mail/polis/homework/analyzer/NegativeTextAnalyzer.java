package ru.mail.polis.homework.analyzer;

public class NegativeTextAnalyzer extends BadWordsAnalyzer {
    
    private static final String[] NEGATIVE_SMILES = {"=(", ":(", ":|"};
    
    @Override
    public FilterType getFilterType() {
        return FilterType.NEGATIVE_TEXT;
    }
    
    @Override
    protected String[] getBadWords() {
        return NEGATIVE_SMILES;
    }
}
