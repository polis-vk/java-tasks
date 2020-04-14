package ru.mail.polis.homework.analyzer;

public class NegativeFilter extends SpamFilter {

    private static final String[] NEGATIVE = {"=(", ":(", ":|"};

    public NegativeFilter() {
        super(NEGATIVE);
    }

    @Override
    public int getFilterPriority() {
        return FilterType.NEGATIVE_TEXT.getPriority();
    }

    @Override
    public FilterType analysis(String str) {
        if (wordSearch(str)) {
            return FilterType.NEGATIVE_TEXT;
        }
        return FilterType.GOOD;
    }
}
