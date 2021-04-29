package ru.mail.polis.homework.analyzer;

public class NegativeTextAnalyzer extends SpamAnalyzer{
    private final static String[] BAD_SMILEYS = {"=(", ":(", ":|"};

    public NegativeTextAnalyzer() {
        super(BAD_SMILEYS);
    }

    @Override
    public FilterType getType() {
        return FilterType.NEGATIVE_TEXT;
    }
}
