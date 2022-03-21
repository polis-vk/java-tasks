package ru.mail.polis.homework.analyzer;

public class NegativeTextAnalyzer extends BadWordsAnalyzer {

    private static final String[] NEGATIVE_TEXT = new String[]{"=(", ":(", ":|"};

    public NegativeTextAnalyzer() {
        badWords = NEGATIVE_TEXT;
        filterType = FilterType.NEGATIVE_TEXT;
    }
}
