package ru.mail.polis.homework.analyzer.filters;

import ru.mail.polis.homework.analyzer.FilterType;
import ru.mail.polis.homework.analyzer.TextAnalyzer;

public class NegativeFilter extends SpamFilter implements TextAnalyzer {
    private final static String[] sadEmotes = new String[]{":(", "=(", ":|"};

    public NegativeFilter() {
        super(sadEmotes);
    }

    @Override
    public final FilterType filterType() {
        return FilterType.NEGATIVE_TEXT;
    }
}
