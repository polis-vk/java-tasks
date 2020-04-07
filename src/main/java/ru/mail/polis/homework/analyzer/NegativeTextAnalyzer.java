package ru.mail.polis.homework.analyzer;

public class NegativeTextAnalyzer implements TextAnalyzer {
    
    private static final String[] negativeSmiles = {"=(", ":(", ":|"};
    
    @Override
    public FilterType getFilterType() {
        return FilterType.NEGATIVE_TEXT;
    }
    
    @Override
    public boolean isCorrect(String text) {
        for (String smile : negativeSmiles) {
            if (text.contains(smile)){
                return false;
            }
        }
        return true;
    }
}
