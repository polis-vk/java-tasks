package ru.mail.polis.homework.analyzer;

import java.util.Arrays;

public class SpamAnalyzer implements TextAnalyzer {
    private final String[] spamElements;

    public SpamAnalyzer(String[] spam) {
        this.spamElements = Arrays.copyOf(spam, spam.length);
    }

    @Override
    public FilterType getType() {
        return FilterType.SPAM;
    }

    @Override
    public FilterType analyze(String text) {
        for (String elementSpam : spamElements) {
            if (text.contains(elementSpam)) {
                return FilterType.SPAM;
            }
        }
        return FilterType.GOOD;
    }
}
