package ru.mail.polis.homework.analyzer;

import java.util.Arrays;

public class SpamFilter implements TextAnalyzer {
    private final String[] spam;
    private static FilterType filterType = FilterType.SPAM;
    protected static int priority;

    public SpamFilter(String[] spam) {
        this.spam = Arrays.copyOf(spam, spam.length);
    }

    @Override
    public int getFilterPriority() {
        return priority;
    }

    @Override
    public FilterType Analysis(String str) {
        if (str == null || str.isEmpty()) {
            return FilterType.GOOD;
        }

        for (String spamWord : spam) {
            if (str.contains(spamWord)) {
                return filterType;
            }
        }
        return FilterType.GOOD;
    }


}
