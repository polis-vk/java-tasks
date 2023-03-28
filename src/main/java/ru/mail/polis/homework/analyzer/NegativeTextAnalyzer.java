package ru.mail.polis.homework.analyzer;

public class NegativeTextAnalyzer extends SpamAnalyzer {
    private static final String[] negativeEmotions = {"=(", ":(", ":|"};

    public NegativeTextAnalyzer() {
        super(negativeEmotions);
    }

    @Override
    public FilterType getType() {
        return FilterType.NEGATIVE_TEXT;
    }
}
