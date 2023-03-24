package ru.mail.polis.homework.analyzer;

public class NegativeTextAnalyzer extends SpamAnalyzer implements TextAnalyzer {
    private static final String[] NEGATIVE_EMOTIONS = new String[]{"=(", ":(", ":|"};

    public NegativeTextAnalyzer() {
        super(NEGATIVE_EMOTIONS);
    }

    @Override
    public FilterType getFilterType() {
        return FilterType.NEGATIVE_TEXT;
    }
}
