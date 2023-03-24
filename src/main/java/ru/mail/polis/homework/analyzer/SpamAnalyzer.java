package ru.mail.polis.homework.analyzer;

import java.util.Arrays;

public class SpamAnalyzer implements TextAnalyzer {
    private final String[] spam;
    private static final FilterType FILTER_TYPE = FilterType.SPAM;

    SpamAnalyzer(String[] spam) {
        this.spam = Arrays.copyOf(spam, spam.length);
    }

    @Override
    public boolean isTextCorrect(String text) {
        return TextAnalyzer.find(text, spam);
    }

    @Override
    public FilterType getFilterType() {
        return FILTER_TYPE;
    }
}
