package ru.mail.polis.homework.analyzer;

public class CustomFilter implements TextAnalyzer {
    private String firstWord;

    public CustomFilter(String beginning) {
        this.firstWord = beginning;
    }

    @Override
    public FilterType analyze(String text) {
        if (text.startsWith(firstWord)) {
            return FilterType.CUSTOM;
        }
        return FilterType.GOOD;
    }

    @Override
    public FilterType priority() {
        return FilterType.CUSTOM;
    }
}
