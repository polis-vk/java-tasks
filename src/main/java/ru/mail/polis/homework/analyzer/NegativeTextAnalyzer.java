package ru.mail.polis.homework.analyzer;

public class NegativeTextAnalyzer implements TextAnalyzer{
    private final FilterType type = FilterType.NEGATIVE_TEXT;

    @Override
    public boolean textAnalysis(String str) {
        for (int i = 1; i < str.length(); i++) {
            if (str.charAt(i) == '(' && (str.charAt(i - 1) == '=' || str.charAt(i - 1) == ':')) {
                return true;
            } else if (str. charAt(i) == '|' && str.charAt(i - 1) == ':') {
                return true;
            }
        }
        return false;
    }

    @Override
    public FilterType getType() {
        return type;
    }
}
