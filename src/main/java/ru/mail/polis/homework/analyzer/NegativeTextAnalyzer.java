package ru.mail.polis.homework.analyzer;

public class NegativeTextAnalyzer implements TextAnalyzer {
    private final String[] smiles = {
            "=(",
            ":(",
            ":|"
    };

    @Override
    public FilterType getType() {
        return FilterType.NEGATIVE_TEXT;
    }

    @Override
    public final byte getPriority() {
        return 3;
    }

    @Override
    public boolean isValid(String text) {
        for(String i : smiles){
            if(text.contains(i)){
                return false;
            }
        }
        return true;
    }
}
