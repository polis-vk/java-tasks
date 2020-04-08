package ru.mail.polis.homework.analyzer;

public class NegativeTextAnalyzer implements TextAnalyzer {
    
    private static final String[] negativeSmiles = {"=(", ":(", ":|"};
    
    @Override
    public FilterType getFilterType() {
        return FilterType.NEGATIVE_TEXT;
    }
    
    @Override
    public boolean isTriggered(String text) {
        SpamAnalyzer spamAnalyzer = new SpamAnalyzer(negativeSmiles);
        return spamAnalyzer.isTriggered(text);
    }
}
