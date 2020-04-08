package ru.mail.polis.homework.analyzer.filters;

import ru.mail.polis.homework.analyzer.FilterType;
import ru.mail.polis.homework.analyzer.TextAnalyzer;

import java.util.Arrays;

public class SpamFilter implements TextAnalyzer {

    private static final FilterType VALUE = FilterType.SPAM;

    private final String[] spamWords;

    public SpamFilter(String[] spam) {
        spamWords = Arrays.copyOf(spam, spam.length);
    }

    @Override
    public FilterType analyze(String str) {
        for (String word : spamWords) {
            if (str.contains(word)) {
                return FilterType.SPAM;
            }
        }
        return null;
    }

    @Override
    public FilterType getValue() {
        return VALUE;
    }
}
