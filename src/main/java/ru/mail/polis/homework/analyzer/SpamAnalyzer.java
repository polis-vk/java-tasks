package ru.mail.polis.homework.analyzer;

import java.util.Arrays;

public class SpamAnalyzer implements TextAnalyzer {
    private final int priority;

    private final String[] spam;

    public SpamAnalyzer(String[] spam, int priority) {
        this.spam = spam;
        this.priority = priority;
    }

    @Override
    public FilterType getType() {
        return FilterType.SPAM;
    }

    @Override
    public final int getPriority() {
        return priority;
    }

    @Override
    public boolean isValid(String text) {
        return Arrays.stream(spam).noneMatch(text::contains);
    }
}
