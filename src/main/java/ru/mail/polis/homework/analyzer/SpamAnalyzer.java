package ru.mail.polis.homework.analyzer;

import java.util.Arrays;

public class SpamAnalyzer implements TextAnalyzer {

    private final String[] spamWords;

    public SpamAnalyzer(String[] spamWords) {
        this.spamWords = spamWords;
    }

    @Override
    public boolean analyze(String text) {
        boolean result = true;
        for (String word : spamWords) {
            if (text.contains(word)) {
                result = false;
                break;
            }
        }
        return result;
    }

    @Override
    public FilterType getFilterType() {
        return FilterType.SPAM;
    }
}
