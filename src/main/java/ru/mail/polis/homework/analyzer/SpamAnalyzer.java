package ru.mail.polis.homework.analyzer;

import java.util.Arrays;

public class SpamAnalyzer implements TextAnalyzer {
    private String[] spam;

    public SpamAnalyzer(String[] spamMessages) {
        if (spamMessages != null) {
            spam = Arrays.copyOf(spamMessages, spamMessages.length);
        }
    }

    @Override
    public FilterType filtering(String text) {
        for (String message : spam) {
            if (text.contains(message)) {
                return FilterType.SPAM;
            }
        }
        return FilterType.GOOD;
    }

    @Override
    public FilterType getReturnType() {
        return FilterType.SPAM;
    }
}
