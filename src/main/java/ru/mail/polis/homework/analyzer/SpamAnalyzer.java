package ru.mail.polis.homework.analyzer;

public class SpamAnalyzer implements TextAnalyzer {
    String[] spamWords;

    public SpamAnalyzer(String[] spamWords) {
        this.spamWords = spamWords;
    }

    @Override
    public boolean isNotCorrectString(String str) {
        for (String spamWord : spamWords) {
            if (str.contains(spamWord)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public int getPriority() {
        return 3;
    }

    @Override
    public FilterType getType() {
        return FilterType.SPAM;
    }
}
