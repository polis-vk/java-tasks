package ru.mail.polis.homework.analyzer;

public class NegativeTextAnalyzer extends SpamAnalyzer implements TextAnalyzer {
    static final int priority = 3;
    static final FilterType analyzerType = FilterType.NEGATIVE_TEXT;

    public NegativeTextAnalyzer() {
        super(new String[]{"=(", ":(", ":|"});
    }

    @Override
    public int getPriority() {
        return priority;
    }

    @Override
    public FilterType getFilterType() {
        return analyzerType;
    }
}
