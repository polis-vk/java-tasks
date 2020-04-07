package ru.mail.polis.homework.analyzer;

public class NegativeTextAnalyzer implements TextAnalyzer {
    
    private static final String[] negativeSmiles = {"=(", ":(", ":|"};
    
    @Override
    public FilterType getFilterType() {
        return FilterType.NEGATIVE_TEXT;
    }
    
    @Override
    public boolean isTriggered(String text) {
        if (text == null || text.isEmpty()) {
            return false;
        }
        for (String smile : negativeSmiles) {
            if (text.contains(smile)) {
                return true;
            }
        }
        return false;
    }
}
