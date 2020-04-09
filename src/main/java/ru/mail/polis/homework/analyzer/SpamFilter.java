package ru.mail.polis.homework.analyzer;

import java.util.Arrays;

public class SpamFilter implements TextAnalyzer {
    private final static FilterType FILTER_TYPE = FilterType.SPAM;
    private final String[] SPAM;

    public SpamFilter(String[] spam) {
        this.SPAM = Arrays.copyOf(spam, spam.length);
    }

    @Override
    public int getFilterPriority() {
        return FILTER_TYPE.getPriority();
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
