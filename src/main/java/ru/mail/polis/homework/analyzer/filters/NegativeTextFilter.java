package ru.mail.polis.homework.analyzer.filters;

import ru.mail.polis.homework.analyzer.FilterType;

public class NegativeTextFilter extends SpamFilter {

    private static final FilterType type = FilterType.NEGATIVE_TEXT;

    private final static String[] negativeLiterals = new String[]{"=(", ":(", ":|"};

    public NegativeTextFilter() {
        super(negativeLiterals);
    }

    @Override
    public FilterType analyze(String text) {
        return super.analyze(text) == defaultType ? defaultType : type;
    }

    @Override
    public FilterType getType() {
        return type;
    }

}
