package ru.mail.polis.homework.analyzer;

import java.util.Arrays;

public class TextSpamAnalyzer implements TextAnalyzer {

    private final String[] spamWords;

    public TextSpamAnalyzer(String[] spamWords) {
        this.spamWords = Arrays.copyOf(spamWords, spamWords.length);
    }

    @Override
    public boolean analyzeText(String text) {
        for (String spamWord : spamWords) {
            if (text.contains(spamWord)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public FilterType getTypeOfFilter() {
        return FilterType.SPAM;
    }
}
