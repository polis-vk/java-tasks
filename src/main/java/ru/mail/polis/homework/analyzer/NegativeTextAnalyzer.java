package ru.mail.polis.homework.analyzer;

public class NegativeTextAnalyzer extends SpamAnalyzer {
    private static final String[] BAD_WORDS = {"=(", ":(", ":|"};

    public NegativeTextAnalyzer() {
        super(BAD_WORDS);
    }

    @Override
    public FilterType getFilterType() {
        return FilterType.NEGATIVE_TEXT;
    }
}
