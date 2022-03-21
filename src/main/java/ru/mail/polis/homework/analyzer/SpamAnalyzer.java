package ru.mail.polis.homework.analyzer;

public class SpamAnalyzer extends BadWordsAnalyzer {

    public SpamAnalyzer(String[] spam) {
        badWords = spam;
        filterType = FilterType.SPAM;
    }
}
