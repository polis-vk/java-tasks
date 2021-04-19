package ru.mail.polis.homework.analyzer;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

public final class BadStringAnalyzer implements TextAnalyzer {

    private final Set<String> spamWords;
    private final int priority;
    private final FilterType filterType;

    public BadStringAnalyzer(String[] spamWords, int priority, FilterType filterType) {
        this.spamWords = Arrays.stream(spamWords)
                .map(String::trim)
                .collect(Collectors.toSet());
        this.priority = priority;
        this.filterType = filterType;
    }

    @Override
    public FilterType analyze(String text) {
        if (text == null || text.isEmpty()) {
            return FilterType.GOOD;
        }
        return spamWords.stream().anyMatch(text::contains) ? filterType : FilterType.GOOD;
    }

    @Override
    public int getPriority() {
        return priority;
    }
}
