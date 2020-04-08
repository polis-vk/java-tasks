package ru.mail.polis.homework.analyzer;

import java.util.Arrays;

public class SpamTextFilter implements TextAnalyzer {

    private static final FilterType type = FilterType.SPAM;
    private final String[] spam;

    public SpamTextFilter(String[] spam) {
        this.spam = spam;
    }

    @Override
    public FilterType analyze(String text) {
        for (String spamWords : spam) {
            if (text.contains(spamWords)) {
                return type;
            }
        }
        return good;
    }
}
