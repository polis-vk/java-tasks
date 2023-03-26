package ru.mail.polis.homework.analyzer;

public class SpamAnalyzer implements TextAnalyzer {
    private final String[] SPAM_WORDS;

    SpamAnalyzer(String[] spamWords) {
        SPAM_WORDS = spamWords;
    }

    public FilterType analyze(String text) {
        for (String spamWord : SPAM_WORDS) {
            if (text.contains(spamWord)) {
                return FilterType.SPAM;
            }
        }
        return FilterType.GOOD;
    }
}
