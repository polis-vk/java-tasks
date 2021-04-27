package ru.mail.polis.homework.analyzer.filters;

import ru.mail.polis.homework.analyzer.FilterType;
import ru.mail.polis.homework.analyzer.TextAnalyzer;

public class NegativeFilter extends SpamFilter {
    private final static String[] SAD_EMOTES = new String[]{":(", "=(", ":|"};

    public NegativeFilter() {
        super(SAD_EMOTES);
    }

    @Override
    public final FilterType filterType() {
        return FilterType.NEGATIVE_TEXT;
    }
}
