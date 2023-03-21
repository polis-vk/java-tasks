package ru.mail.polis.homework.analyzer;

public class SpamAnalyzer implements TextAnalyzer {
    String[] spamWords;

    SpamAnalyzer(String[] spamWords) {
        this.spamWords = spamWords;
    }

    public FilterType analyze(String text) {
        for (String spamWord :
                spamWords) {
            if (text.contains(spamWord)) {
                return FilterType.SPAM;
            }
        }

        return FilterType.GOOD;
    }

}
