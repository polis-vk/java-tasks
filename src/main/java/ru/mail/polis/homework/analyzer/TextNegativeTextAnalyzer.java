package ru.mail.polis.homework.analyzer;

public class TextNegativeTextAnalyzer implements TextAnalyzer {

    private final static String[] NEGATIVE_MORPHEMES = {"=(", ":(", ":|"};

    @Override
    public boolean analyze(String text) {
        return UtilsAnalyzer.isContainsMorphemes(text, NEGATIVE_MORPHEMES);
    }

    @Override
    public FilterType getType() {
        return FilterType.NEGATIVE_TEXT;
    }
}
