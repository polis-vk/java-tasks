package ru.mail.polis.homework.analyzer;

public class SpamAnalyzer implements TextAnalyzer, TextScanning {
    private final String[] spamWords;

    public SpamAnalyzer(String[] spamWords) {
        this.spamWords = spamWords;
    }

    @Override
    public boolean isNotCorrectString(String str) {
        return containsBadSymbols(str, spamWords);
    }

    @Override
    public FilterType getType() {
        return FilterType.SPAM;
    }
}
