package ru.mail.polis.homework.analyzer;

public class NegativeTextAnalyzer extends SpamAnalyzer {
    private final byte priority = 3;
    private static final String[] negative = { "=(", ":(", ":|" };

    public NegativeTextAnalyzer() {
        super(negative);
    }

    public FilterType filter() {
        return FilterType.NEGATIVE_TEXT;
    }

    public byte priority() {
        return priority;
    }
}
