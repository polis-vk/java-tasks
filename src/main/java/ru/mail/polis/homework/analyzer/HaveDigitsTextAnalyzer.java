package ru.mail.polis.homework.analyzer;

public class HaveDigitsTextAnalyzer implements TextAnalyzer {

    @Override
    public FilterType analyze(String str) {
        if (str == null) return FilterType.GOOD;
        for (int i = 0; i < str.length(); i++) {
            if (Character.isDigit(str.charAt(i))) {
                return FilterType.CUSTOM;
            }
        }
        return FilterType.GOOD;
    }

    @Override
    public FilterType getFilterType() {
        return FilterType.CUSTOM;
    }
}
