package ru.mail.polis.homework.analyzer;

public class SpamAnalyzer implements TextAnalyzer {
    private final String[] spamWords;

    public SpamAnalyzer(String[] spamWords) {
        this.spamWords = spamWords;
    }

    public FilterType analyze(String text) {
        return analyze(text, spamWords, FilterType.SPAM);
    }
}
