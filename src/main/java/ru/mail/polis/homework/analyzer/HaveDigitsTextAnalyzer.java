package ru.mail.polis.homework.analyzer;

public class HaveDigitsTextAnalyzer implements TextAnalyzer {

    @Override
    public FilterType analyze(String str) {
        if (str == null) {
            return FilterType.GOOD;
        }
        for (char ch : str.toCharArray()) {
            if (Character.isDigit(ch)) {
                return getFilterType();
            }
        }
        return FilterType.GOOD;
    }

    @Override
    public FilterType getFilterType() {
        return FilterType.CUSTOM;
    }
    /* Код для второй версии компаратора */
    private int priority = 4;

    public int getPriority() {
        return priority;
    }
    /*----------------------------*/
}
