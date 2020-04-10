package ru.mail.polis.homework.analyzer;

import java.util.Arrays;

public class SpamFilter implements TextAnalyzer {

    private final String[] spam;


    public SpamFilter(String[] spam) {

        this.spam = Arrays.copyOf(spam, spam.length);
    }


    @Override
    public FilterType analyze(String str) {
        for (String word : spam) {
            if (str.contains(word)) {
                return FilterType.SPAM;
            }
        }
        return null;
    }

    @Override
    public long getPriority() {
        return FilterType.SPAM.getPriority();
    }

}
