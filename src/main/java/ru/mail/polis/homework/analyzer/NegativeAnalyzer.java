package ru.mail.polis.homework.analyzer;

public class NegativeAnalyzer implements TextAnalyzer {

    private final FilterType type = FilterType.NEGATIVE_TEXT;

    @Override
    public boolean isAnalyze(String str) {
        for (int i = 0; i < str.length(); i++) {
            if (str.charAt(i) == '=' && str.charAt(i + 1) == '(') {
                return false;
            } else if (str.charAt(i) == ':' && (str.charAt(i + 1) == '(' || str.charAt(i + 1) == '|')) {
                return false;
            }
        }
        return true;
    }

    @Override
    public FilterType getType() {
        return type;
    }
}
