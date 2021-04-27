package ru.mail.polis.homework.analyzer;

public class PunctuationAnalyzer implements TextAnalyzer {
    private final static String[] PUNCTUATION_MARKS = {".", ",", ":", ";", "?", "-", "!"};

    @Override
    public FilterType analyze(String text) {
        for (String mark : PUNCTUATION_MARKS) {
            if (text.contains(mark)) {
                return FilterType.GOOD;
            }
        }
        return FilterType.CUSTOM;
    }

    @Override
    public FilterType getType() {
        return FilterType.CUSTOM;
    }
}
