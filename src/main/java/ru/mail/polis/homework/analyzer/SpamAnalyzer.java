package ru.mail.polis.homework.analyzer;

import java.util.Arrays;

public class SpamAnalyzer implements TextAnalyzer {
    private final String[] spam;
    private static final FilterType FILTER_TYPE = FilterType.SPAM;

    public SpamAnalyzer(String[] spam) {
        this.spam = Arrays.copyOf(spam, spam.length);
    }

    @Override
    public boolean isTextCorrect(String text) {
        for (String str : this.spam) {
            if (text.contains(str)) {
                return false;
            }
        }
        return true;
    }

    @Override
    public FilterType getFilterType() {
        return FILTER_TYPE;
    }
}
