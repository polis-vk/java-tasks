package ru.mail.polis.homework.analyzer;

public class NegativeTextAnalyzer extends SpamAnalyzer {
    private final static String[] BAD_EMOTIONS = {"=(", ":(", ":|"};

    public NegativeTextAnalyzer() {
        super(BAD_EMOTIONS);
    }

    @Override
    public FilterType getType() {
        return FilterType.NEGATIVE_TEXT;
    }
}
