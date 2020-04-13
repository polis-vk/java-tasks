package ru.mail.polis.homework.analyzer;

public class NegativeTextFilter extends SpamTextFiler {

    private final static FilterType TYPE_FILTER = FilterType.NEGATIVE_TEXT;
    private final static String[] negativeWords = {"=(", ":(", ":|"};

    public NegativeTextFilter() {
        super(negativeWords);
    }

    @Override
    public FilterType getFilterType() {
        return TYPE_FILTER;
    }
}