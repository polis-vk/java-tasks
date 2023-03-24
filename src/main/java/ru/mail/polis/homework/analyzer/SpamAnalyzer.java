package ru.mail.polis.homework.analyzer;

import java.util.ArrayList;
import java.util.Arrays;

public class SpamAnalyzer implements TextAnalyzer {
    String[] spam;
    final static char priority = 0;

    public SpamAnalyzer(String[] spamMessages) {
        if(spamMessages != null) {
            spam = Arrays.copyOf(spamMessages, spamMessages.length);
        }
    }

    @Override
    public FilterType filtering(String text) {
        for (String spamMessage : spam) {
            if (text.contains(spamMessage)) {
                return FilterType.SPAM;
            }
        }
        return FilterType.GOOD;
    }

    @Override
    public char getPriority() {
        return SpamAnalyzer.priority;
    }
}
