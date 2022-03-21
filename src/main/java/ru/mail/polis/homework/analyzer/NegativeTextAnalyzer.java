package ru.mail.polis.homework.analyzer;

public class NegativeTextAnalyzer extends SpamAnalyzer {

    private static final String[] BAD_EMOTIONS = new String[]{"=(", ":(", ":|"};

    NegativeTextAnalyzer() {
        super(BAD_EMOTIONS);
    }

    public FilterType getType() {
        return FilterType.NEGATIVE_TEXT;
    }
}
