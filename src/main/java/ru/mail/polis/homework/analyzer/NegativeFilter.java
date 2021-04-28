package ru.mail.polis.homework.analyzer;

public class NegativeFilter extends SpamFilter {
    private static final String[] NEGATIVE_LIST = new String[]{"=(", ":(", ":|"};

    public NegativeFilter() {
        super(NEGATIVE_LIST);
    }

    @Override
    public FilterType type() {
        return FilterType.NEGATIVE_TEXT;
    }
}
