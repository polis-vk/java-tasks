package ru.mail.polis.homework.analyzer;

public class NegativeTextAnalyzer extends BadWordsAnalyzer {
    private static final String[] NEGATIVE_WORDS = new String[]{"=(", ":(", ":|"};

    public NegativeTextAnalyzer() {
        wordList = NEGATIVE_WORDS;
        filterType = FilterType.NEGATIVE_TEXT;
    }
}
