package ru.mail.polis.homework.analyzer;

public class CustomAnalyzer implements TextAnalyzer {
    @Override
    public FilterType analyze(String text) {
        return (text.equals(text.toUpperCase())) ? FilterType.CUSTOM : FilterType.GOOD;
    }
}
