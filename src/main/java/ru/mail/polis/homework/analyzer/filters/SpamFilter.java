package ru.mail.polis.homework.analyzer.filters;

import ru.mail.polis.homework.analyzer.FilterType;

import java.util.Arrays;

public class SpamFilter extends ContainsAbstractFilter {

    private final String[] spam;

    public SpamFilter(String[] spam) {
        this.spam = Arrays.copyOf(spam, spam.length);
    }

    @Override
    public FilterType analyze(String text) {
        return analyze(spam,text) ==FilterType.GOOD ? FilterType.GOOD : FilterType.SPAM;
    }

    @Override
    public FilterType getType() {
        return FilterType.SPAM;
    }
}
