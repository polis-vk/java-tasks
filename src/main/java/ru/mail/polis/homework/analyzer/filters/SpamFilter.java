package ru.mail.polis.homework.analyzer.filters;

import ru.mail.polis.homework.analyzer.FilterType;
import ru.mail.polis.homework.analyzer.TextAnalyzer;

import java.util.Arrays;

public class SpamFilter extends Search implements TextAnalyzer {
    private final String[] spamWords;

    public SpamFilter(String[] spam) {
        spamWords = Arrays.copyOf(spam, spam.length);
    }

    @Override
    public FilterType analyze(String str) {
        return check(str, spamWords, FilterType.SPAM);
    }

    @Override
    public FilterType getType() {
        return FilterType.SPAM;
    }
}
