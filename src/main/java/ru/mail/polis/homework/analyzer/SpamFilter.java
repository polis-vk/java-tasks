package ru.mail.polis.homework.analyzer;

import java.util.Arrays;

public class SpamFilter implements TextAnalyzer {
    private final String[] SPAM;
    private final static FilterType FILTER_TYPE = FilterType.SPAM;
    private static int priority;

    public SpamFilter(String[] spam) {
        this.SPAM = Arrays.copyOf(spam, spam.length);
    }

    @Override
    public int getFilterPriority() {
        return priority;
    }

    @Override
    public void setFilterPriority(int priority) {
        this.priority = priority;
    }

    @Override
    public FilterType getFilterType() {
        return FILTER_TYPE;
    }

    @Override
    public FilterType analysis(String str) {
        if (wordSearch(str)) {
            return FILTER_TYPE;
        }
        return FilterType.GOOD;
    }

    protected boolean wordSearch(String str) {
        if (str == null || str.isEmpty()) {
            return false;
        }

        for (String spamWord : SPAM) {
            if (str.contains(spamWord)) {
                return true;
            }
        }
        return false;
    }


}
