package ru.mail.polis.homework.analyzer;

public class NegativeTextAnalyzer extends SpamAnalyzer {
    private final static String[] NEGATIVE_ITEMS = new String[]{"=(", ":(", ":|"};

    public NegativeTextAnalyzer() {
        super(NEGATIVE_ITEMS);
    }

    @Override
    public FilterType filterType() {
        return FilterType.NEGATIVE_TEXT;
    }

}
