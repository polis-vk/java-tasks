package ru.mail.polis.homework.analyzer;

import java.util.Arrays;

public class SpamAnalyzer implements TextAnalyzer {
    private final String[] SPAM_WORDS;

    public SpamAnalyzer(String[] spam) {
        this.SPAM_WORDS = Arrays.copyOf(spam, spam.length);
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

        for (String spamWord : SPAM_WORDS) {
            if (text.contains(spamWord)) {
                return false;
            }
        }
        return true;
    }

}
