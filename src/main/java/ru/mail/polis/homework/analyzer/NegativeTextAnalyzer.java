package ru.mail.polis.homework.analyzer;

public class NegativeTextAnalyzer extends SpamAnalyzer implements TextAnalyzer {
    private static final String[] negativeWords = new String[]{"=(", ":(", ":|"};

    public NegativeTextAnalyzer() {
        super(negativeWords);
    }

    @Override
    public FilterType getFilterType() {
        return FilterType.NEGATIVE_TEXT;
    }
}
