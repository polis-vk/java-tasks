package ru.mail.polis.homework.analyzer.filters;

import ru.mail.polis.homework.analyzer.FilterType;

public class PositiveTextAnalyzer extends SpamAnalyzer {

    private final static String[] POSITIVE_SMILEYS = new String[]{"=)", ":)", ".)", ";)"};

    public PositiveTextAnalyzer() {
        super(POSITIVE_SMILEYS);
    }

    @Override
    public FilterType getType() {
        return FilterType.CUSTOM;
    }
}
