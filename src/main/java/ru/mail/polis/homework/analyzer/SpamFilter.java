package ru.mail.polis.homework.analyzer;

import java.util.Arrays;

public class SpamFilter implements TextAnalyzer {
    private final String[] spamList;

    public SpamFilter(String[] spamList) {
        this.spamList = Arrays.copyOf(spamList, spamList.length);
    }

    public FilterType type() {
        return FilterType.SPAM;
    }

    public boolean isValid(String text) {
        if (text != null && !text.isEmpty()) {
            for (String spam : spamList) {
                if (text.contains(spam)) {
                    return false;
                }
            }
        }

        return true;
    }
}
