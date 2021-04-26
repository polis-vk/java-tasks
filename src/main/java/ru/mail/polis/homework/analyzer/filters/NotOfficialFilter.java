package ru.mail.polis.homework.analyzer.filters;

import ru.mail.polis.homework.analyzer.FilterType;
import ru.mail.polis.homework.analyzer.TextAnalyzer;

public class NotOfficialFilter extends SpamFilter implements TextAnalyzer {
    private final static String[] abbreviations = new String[]{"'m", "'re", "'ll", "'d", "'s"};

    public NotOfficialFilter() {
        super(abbreviations);
    }

    @Override
    public final FilterType filterType() {
        return FilterType.NOT_OFFICIAL;
    }
}
