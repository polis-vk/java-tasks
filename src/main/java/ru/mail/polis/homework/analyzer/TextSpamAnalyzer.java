package ru.mail.polis.homework.analyzer;

public class TextSpamAnalyzer implements TextAnalyzer {

    private final String[] spamMorphemes;

    public TextSpamAnalyzer(String[] spamMorphemes) {
        this.spamMorphemes = spamMorphemes;
    }

    @Override
    public boolean analyze(String text) {
        return UtilsAnalyzer.isContainsMorphemes(text, spamMorphemes);
    }

    @Override
    public FilterType getType() {
        return FilterType.SPAM;
    }
}
