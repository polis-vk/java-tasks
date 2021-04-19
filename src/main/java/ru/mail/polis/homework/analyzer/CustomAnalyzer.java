package ru.mail.polis.homework.analyzer;

public class CustomAnalyzer implements TextAnalyzer {

    @Override
    public boolean analyze(String text) {
        if (text == null) {
            return false;
        }
        return text.contains("java");
    }

    @Override
    public FilterType getFilterType() {
        return FilterType.CUSTOM;
    }
}
