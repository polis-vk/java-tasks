package ru.mail.polis.homework.analyzer.filters;

import ru.mail.polis.homework.analyzer.FilterType;
import ru.mail.polis.homework.analyzer.TextAnalyzer;

import java.util.Arrays;

public class SpamFilter implements TextAnalyzer {

    private static final FilterType type = FilterType.SPAM;
    private final String[] spam;

    public SpamFilter(String[] spam) {
        this.spam = Arrays.copyOf(spam, spam.length);
    }

    @Override
    public FilterType analyze(String text) {
        for (String spamElement : spam) {
            if (text.contains(spamElement)) {
                return type;
            }
        }
        return defaultType;
    }

    @Override
    public FilterType getType() {
        return type;
    }
}
