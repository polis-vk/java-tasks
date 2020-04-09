package ru.mail.polis.homework.analyzer;

public class NegativeTextFilter extends SpamTextFiler {

    private final static FilterType typeFilter = FilterType.NEGATIVE_TEXT;
    private final static String[] negativeWords = {"=(", ":(", ":|"};

    public NegativeTextFilter() {
        super(negativeWords);
    }

    @Override
    public FilterType getFilterType() {
        return typeFilter;
    }

    @Override
    public FilterType analyze(String str) {
        return findInText(negativeWords, str, typeFilter);
    }

}
