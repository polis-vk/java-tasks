package ru.mail.polis.homework.analyzer;

import java.util.Arrays;

public class SpamFilter implements TextAnalyzer {

    private final String[] spam;

    public SpamFilter(String[] spam) {
        this.spam = Arrays.copyOf(spam, spam.length);
    }

    @Override
    public int getFilterPriority() {
        return FilterType.SPAM.getPriority();
    }

    @Override
    public FilterType analysis(String str) {
        if (wordSearch(str)) {
            return FilterType.SPAM;
        }
        return FilterType.GOOD;
    }

    protected boolean wordSearch(String str) {
        if (str == null || str.isEmpty()) {
            return false;
        }

        for (String spamWord : spam) {
            if (str.contains(spamWord)) {
                return true;
            }
        }
        return false;
    }
}
