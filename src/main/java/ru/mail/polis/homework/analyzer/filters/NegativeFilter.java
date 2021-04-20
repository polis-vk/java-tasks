package ru.mail.polis.homework.analyzer.filters;

import ru.mail.polis.homework.analyzer.FilterType;

public class NegativeFilter extends SpamFilter {
    private final static int rank = 3;
    private final static String[] sadEmotes = new String[]{":(","=(",":|"};

    public NegativeFilter() {
        super(sadEmotes);
    }

    @Override
    public FilterType filterType() {
        return FilterType.NEGATIVE_TEXT;
    }

    @Override
    public int getRank() {
        return rank;
    }
}
