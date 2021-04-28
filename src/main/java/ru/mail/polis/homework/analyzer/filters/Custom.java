package ru.mail.polis.homework.analyzer.filters;

import ru.mail.polis.homework.analyzer.FilterType;

public class Custom extends SpamFilter{
    private static final String[] BAD_WORD = new String[]{"beach","fork","sock"};
    private static final int PRIORITY = 3;

    public Custom() {
        super(BAD_WORD);
    }

    @Override
    public FilterType filterType() {
        return FilterType.CUSTOM;
    }

    @Override
    public int getPriority() {
        return PRIORITY;
    }
}
