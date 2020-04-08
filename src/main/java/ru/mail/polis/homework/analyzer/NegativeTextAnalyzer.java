package ru.mail.polis.homework.analyzer;

public class NegativeTextAnalyzer extends SpamAnalyzer {
    private static final String[] NEG_EMOTION = {"=(", ":(", ":|"};

    public NegativeTextAnalyzer() {
        super(NEG_EMOTION);
    }

    public boolean check(String text) {
        return super.check(text);
    }

    public FilterType getFilterType() {
        return FilterType.NEGATIVE_TEXT;
    }
}


