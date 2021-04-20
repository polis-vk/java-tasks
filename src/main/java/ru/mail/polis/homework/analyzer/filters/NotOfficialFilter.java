package ru.mail.polis.homework.analyzer.filters;

import ru.mail.polis.homework.analyzer.FilterType;

public class NotOfficialFilter extends SpamFilter{
    private final static int rank = 4;
    private final static String[] abbreviations = new String[]{"'m","'re","'ll","'d","'s"};

    public NotOfficialFilter() {
        super(abbreviations);
    }

    @Override
    public FilterType filterType() {
        return FilterType.NOT_OFFICIAL;
    }

    @Override
    public int getRank() {
        return rank;
    }
}
