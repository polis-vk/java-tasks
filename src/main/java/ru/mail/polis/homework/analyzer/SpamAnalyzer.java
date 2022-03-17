package ru.mail.polis.homework.analyzer;

public class SpamAnalyzer extends BadWordsAnalyzer {
    public SpamAnalyzer(String[] spam) {
        filterType = FilterType.SPAM;
        badWords = spam;
    }
}
