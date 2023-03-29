package ru.mail.polis.homework.analyzer;


import java.util.Arrays;

public class SpamFilter implements TextAnalyzer {
    private String[] spamList;

    SpamFilter(String[] spamArray) {
        if (spamArray != null) {
            this.spamList = Arrays.copyOf(spamArray, spamArray.length);
        }
    }

    @Override
    public FilterType analyze(String str) {
        if (str == null || str.isEmpty()) {
            return FilterType.GOOD;
        }

        for (String x : spamList) {
            if (str.contains(x)) {
                return FilterType.SPAM;
            }
        }
        return FilterType.GOOD;
    }

    @Override
    public int getPriority() {
        return FilterType.SPAM.getType();
    }
}
