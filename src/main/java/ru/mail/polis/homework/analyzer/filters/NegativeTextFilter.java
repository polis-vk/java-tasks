package ru.mail.polis.homework.analyzer.filters;

import ru.mail.polis.homework.analyzer.FilterType;

public class NegativeTextFilter extends SpamFilter {

    private final static String[] NEGATIVE_LITERALS = new String[]{"=(", ":(", ":|"};

    public NegativeTextFilter() {
        super(NEGATIVE_LITERALS);
    }

    @Override
    public FilterType getType() {
        return FilterType.NEGATIVE_TEXT;
    }

}
