package ru.mail.polis.homework.analyzer;

public class SpamAnalyzer implements TextAnalyzer {
    private final String[] spamWords;
    private final int priority = 3;

    public SpamAnalyzer(String[] spamWords) {
        this.spamWords = spamWords;
    }

    @Override
    public boolean isNotCorrectString(String str) {
        return containsBadSymbols(str,spamWords);
    }

    @Override
    public int getPriority() {
        return priority;
    }

    @Override
    public FilterType getType() {
        return FilterType.SPAM;
    }
}
