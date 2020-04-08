package ru.mail.polis.homework.analyzer;

public class NegativeFilter extends SpamFilter {
    private static int priority;
    private static final FilterType FILTER_TYPE = FilterType.NEGATIVE_TEXT;
    private static final String[] NEGATIVE = {"=(", ":(", ":|"};

    public NegativeFilter() {
        super(NEGATIVE);
    }

    @Override
    public int getFilterPriority() {
        return priority;
    }

    @Override
    public void setFilterPriority(int priority) {
        this.priority = priority;
    }

    @Override
    public FilterType getFilterType() {
        return FILTER_TYPE;
    }

    @Override
    public FilterType analysis(String str) {
        if (wordSearch(str)) {
            return FILTER_TYPE;
        }
        return FilterType.GOOD;
    }
}
