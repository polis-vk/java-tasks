package ru.mail.polis.homework.analyzer;

public class TextSpamAnalyzer implements TextAnalyzer {

    private final String[] spamMorphemes;

    public TextSpamAnalyzer(String[] spamMorphemes) {
        this.spamMorphemes = spamMorphemes;
    }

    @Override
    public boolean analyze(String text) {
        for (String s : spamMorphemes) {
            if (text.contains(s)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public FilterType getType() {
        return FilterType.SPAM;
    }
}
