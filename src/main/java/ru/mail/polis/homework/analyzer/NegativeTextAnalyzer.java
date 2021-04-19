package ru.mail.polis.homework.analyzer;

import java.util.Arrays;

public class NegativeTextAnalyzer implements TextAnalyzer {
    private static final String[] NEGATIVE = {"=(", ":(", ":|"};
    private final int priority;

    public NegativeTextAnalyzer(int priority) {
        this.priority = priority;
    }

    @Override
    public FilterType getType() {
        return FilterType.NEGATIVE_TEXT;
    }

    @Override
    public final int getPriority() {
        return priority;
    }

    @Override
    public boolean isValid(String text) {
        return Arrays.stream(NEGATIVE).noneMatch(text::contains);
    }
}
