package ru.mail.polis.homework.analyzer;

import java.util.Arrays;

public class SpamFilter implements TextAnalyzer {

    public static long priority;
    private final String[] spam;
    protected FilterType type = FilterType.SPAM;

    public SpamFilter(String[] spam) {

        this.spam = Arrays.copyOf(spam, spam.length);
    }

    @Override
    public long getPriority() {
        return priority;
    }

    @Override
    public FilterType analyze(String str) {
        for (String word : spam) {
            if (str.contains(word)) {
                return type;
            }
        }
        return null;
    }

}
