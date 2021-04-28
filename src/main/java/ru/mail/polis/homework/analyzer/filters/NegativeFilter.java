package ru.mail.polis.homework.analyzer.filters;

import ru.mail.polis.homework.analyzer.FilterType;

public class NegativeFilter extends SpamFilter {
    private static final int PRIORITY = 2;
    private static final String[] SAD_EMOTES = new String[]{":(","=(",":|"};
    public NegativeFilter() {
        super(SAD_EMOTES);
    }

    @Override
    public int getPriority() {
        return PRIORITY;
    }

    @Override
    public FilterType filterType() {
        return FilterType.NEGATIVE_TEXT;
    }


}
