package ru.mail.polis.homework.analyzer;

public class CustomFilter implements TextAnalyzer {

    @Override
    public boolean filterIsPassed(String text) {
        String str = text.substring(0, 1);
        if (str.toLowerCase().equals(str)) {
            return true;
        }
        return false;
    }

    @Override
    public FilterType getFilterType() {

        return FilterType.CUSTOM;
    }
}
