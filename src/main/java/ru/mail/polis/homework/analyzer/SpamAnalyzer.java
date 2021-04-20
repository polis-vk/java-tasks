package ru.mail.polis.homework.analyzer;

import java.util.Arrays;

public class SpamAnalyzer implements TextAnalyzer {
    private final String[] spam;

    public SpamAnalyzer(String[] spam) {
        this.spam = spam;
    }

    @Override
    public FilterType getType() {
        return FilterType.SPAM;
    }


    @Override
    public boolean isValid(String text) {
        return Arrays.stream(spam).noneMatch(text::contains);
    }
}
