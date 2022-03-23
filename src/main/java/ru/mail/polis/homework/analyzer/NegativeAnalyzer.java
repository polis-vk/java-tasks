package ru.mail.polis.homework.analyzer;

public class NegativeAnalyzer implements TextAnalyzer {
    protected String[] smiles = {"=(", ":(", ":|"};
    protected FilterType result = FilterType.NEGATIVE_TEXT;

    public void setResult(FilterType result) {
        this.result = result;
    }

    public void setSmiles(String[] smiles) {
        this.smiles = smiles;
    }

    @Override
    public FilterType analyze(String text) {

        if (text == null) {
            return FilterType.GOOD;
        }
        for (String smile : smiles) {
            if (text.contains(smile)) {
                return result;
            }
        }
        return FilterType.GOOD;
    }
}