package ru.mail.polis.homework.analyzer;

import java.util.Arrays;

public class SpamAnalyzer implements TextAnalyzer {
    private final static int SIGNIFICANCE = 1;
    private final String[] spam;

    public SpamAnalyzer(String[] spam) {
        this.spam = Arrays.copyOf(spam, spam.length);
    }

    @Override
    public int getSignificance() {
        return SIGNIFICANCE;
    }

    @Override
    public FilterType analyze(String text) {
        for (String elementSpam : spam) {
            if (text.contains(elementSpam)) {
                return FilterType.SPAM;
            }
        }
        return FilterType.GOOD;
    }
}
