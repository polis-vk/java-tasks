package ru.mail.polis.homework.analyzer;

public class NegativeTextAnalyzer implements TextAnalyzer{

    @Override
    public boolean analyzeText(String text) {
        if (text == null || text.isEmpty()) {
            return false;
        }
        for (int i=0; i<text.length()-1; i++) {
            if (text.charAt(i) == '=' && text.charAt(i+1) == '(') {
                return true;
            }
            if (text.charAt(i) == ':' && text.charAt(i+1) == '(') {
                return true;
            }
            if (text.charAt(i) == ':' && text.charAt(i+1) == '|') {
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
