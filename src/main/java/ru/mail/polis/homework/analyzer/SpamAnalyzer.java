package ru.mail.polis.homework.analyzer;

import java.util.Arrays;

public class SpamAnalyzer implements TextAnalyzer {
    private final String[] spamWords;

    public SpamAnalyzer(String[] spam) {
        this.spamWords = Arrays.copyOf(spam, spam.length);
    }

    @Override
    public FilterType getTypeFilter() {
        return FilterType.SPAM;
    }

    @Override
    public boolean isCorrect(String text) {
        if (text == null || text.isEmpty()) {
            return true;
        }

        for (String spamWord : spamWords) {
            if (text.contains(spamWord)) {
                return false;
            }
        }
        return true;
    }

}
