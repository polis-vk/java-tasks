package ru.mail.polis.homework.analyzer;

import java.util.Arrays;

public class SpamAnalyzer implements TextAnalyzer {
    String[] spam;
    private static final byte priority = 1;

    SpamAnalyzer(String[] spam) {
        this.spam = Arrays.copyOf(spam, spam.length);
    }

    @Override
    public boolean isTextCorrect(String text) {
        for (String word : spam) {
            if (text.contains(word)) {
                return false;
            }
        }
        return true;
    }

    @Override
    public FilterType getFilterType() {
        return FilterType.SPAM;
    }

    @Override
    public byte getPriority() {
        return priority;
    }
}
