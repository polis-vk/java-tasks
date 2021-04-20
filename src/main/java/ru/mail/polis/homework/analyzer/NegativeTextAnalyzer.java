package ru.mail.polis.homework.analyzer;

public class NegativeTextAnalyzer extends SpamAnalyzer {
    private final static String[] negativeItems = new String[]{"=(", ":(", ":|"};

    public NegativeTextAnalyzer() {
        super(negativeItems);
    }

    @Override
    public FilterType filterType() {
        return FilterType.NEGATIVE_TEXT;
    }

}
