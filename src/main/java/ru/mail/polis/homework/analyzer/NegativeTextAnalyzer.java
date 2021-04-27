package ru.mail.polis.homework.analyzer;

public class NegativeTextAnalyzer extends StringArrayAnalyzer {
    private final static String[] NEGATIVE = {"=(", ":(", ":|"};

    public NegativeTextAnalyzer() {
        super(NEGATIVE);
    }

    @Override
    public FilterType getType() {
        return FilterType.NEGATIVE_TEXT;
    }
}
