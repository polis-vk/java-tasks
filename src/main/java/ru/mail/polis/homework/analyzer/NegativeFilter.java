package ru.mail.polis.homework.analyzer;

public class NegativeFilter extends SpamFilter {
    private static final FilterType FILTER_TYPE = FilterType.NEGATIVE_TEXT;
    private static final String[] NEGATIVE = {"=(", ":(", ":|"};

    public NegativeFilter() {
        super(NEGATIVE);
    }

    @Override
    public int getFilterPriority() {
        return FILTER_TYPE.getPriority();
    }

    @Override
    public FilterType analysis(String str) {
        if (wordSearch(str)) {
            return FILTER_TYPE;
        }
        return FilterType.GOOD;
    }
}
