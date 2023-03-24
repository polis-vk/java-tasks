package ru.mail.polis.homework.analyzer;

public class NegativeTextAnalyzer extends SpamAnalyzer implements TextAnalyzer {
    private static final String[] NEGATIVE_EMOTIONS = new String[]{"=(", ":(", ":|"};
    private static final FilterType FILTER_TYPE = FilterType.NEGATIVE_TEXT;

    public NegativeTextAnalyzer() {
        super(NEGATIVE_EMOTIONS);
    }

    @Override
    public FilterType getFilterType() {
        return FILTER_TYPE;
    }
}
