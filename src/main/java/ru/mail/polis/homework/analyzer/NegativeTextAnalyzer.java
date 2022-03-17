package ru.mail.polis.homework.analyzer;

public class NegativeTextAnalyzer extends BadWordsAnalyzer {
    public NegativeTextAnalyzer() {
        filterType = FilterType.NEGATIVE_TEXT;
        badWords = new String[]{"=(", ":(", ":|"};
    }
}
