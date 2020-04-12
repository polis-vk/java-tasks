package ru.mail.polis.homework.analyzer.filters;

import ru.mail.polis.homework.analyzer.FilterType;

public class NegativeTextFilter extends ContainsAbstractFilter {

    private final static String[] NEGATIVE_LITERALS = new String[]{"=(", ":(", ":|"};

    @Override
    public FilterType analyze(String text) {
        return analyze(NEGATIVE_LITERALS, text) == FilterType.GOOD ? FilterType.GOOD : FilterType.NEGATIVE_TEXT;
    }

    @Override
    public FilterType getType() {
        return FilterType.NEGATIVE_TEXT;
    }

}
