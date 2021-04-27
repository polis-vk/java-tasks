package ru.mail.polis.homework.analyzer.filters;

import ru.mail.polis.homework.analyzer.FilterType;
import ru.mail.polis.homework.analyzer.TextAnalyzer;

public class NotOfficialFilter extends SpamFilter {
    private final static String[] ABBREVIATIONS = new String[]{"'m", "'re", "'ll", "'d", "'s"};

    public NotOfficialFilter() {
        super(ABBREVIATIONS);
    }

    @Override
    public final FilterType filterType() {
        return FilterType.NOT_OFFICIAL;
    }
}
