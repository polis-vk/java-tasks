package ru.mail.polis.homework.analyzer;

import java.util.Arrays;

public class SpamAnalyzer implements TextAnalyzer {
    private final String[] SPAM;

    public SpamAnalyzer(String[] spam) { this.SPAM = Arrays.copyOf(spam, spam.length);}

    @Override
    public FilterType analyze(String text) {
        for (String spamWord : SPAM) {
            if (text.contains(spamWord)) {
                return getType();
            }
        }
        return FilterType.GOOD;
    }

    @Override
    public FilterType getType() {
        return FilterType.SPAM;
    }
}


