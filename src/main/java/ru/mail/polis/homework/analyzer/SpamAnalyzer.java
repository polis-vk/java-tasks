package ru.mail.polis.homework.analyzer;

import java.util.Arrays;

public class SpamAnalyzer implements TextAnalyzer {
    private String[] spamMessages;

    public SpamAnalyzer(String[] spamMessages) {
        if (spamMessages != null) {
            this.spamMessages = Arrays.copyOf(spamMessages, spamMessages.length);
        }
    }

    @Override
    public FilterType filtering(String text) {
        for (String spamMessage : spamMessages) {
            if (text.contains(spamMessage)) {
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
