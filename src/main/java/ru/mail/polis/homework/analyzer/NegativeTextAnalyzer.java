package ru.mail.polis.homework.analyzer;

public class NegativeTextAnalyzer extends SpamAnalyzer {
    private static final FilterType FILTER_TYPE = FilterType.NEGATIVE_TEXT;
    private static final String[] NEGATIVE_TEXT = {"=(", ":(", ":|"};

    public NegativeTextAnalyzer() {
        super(NEGATIVE_TEXT);
    }

    @Override
    public FilterType filterType() {
        return FILTER_TYPE;
    }
}
