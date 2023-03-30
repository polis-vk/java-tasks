package ru.mail.polis.homework.analyzer;

public class NegativeTextAnalyzer extends SpamAnalyzer {
    private static final String[] NEGATIVE_WORDS = new String[]{"=(", ":(", ":|"};

    public NegativeTextAnalyzer() {
        super(NEGATIVE_WORDS);
    }

    @Override
    public FilterType getFilterType() {
        return FilterType.NEGATIVE_TEXT;
    }
}
