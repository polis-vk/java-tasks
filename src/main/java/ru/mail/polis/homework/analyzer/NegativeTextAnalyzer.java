package ru.mail.polis.homework.analyzer;

public class NegativeTextAnalyzer extends NegativeTextOrSpamAnalyzer{

    private static final String[] NEGATIVE_TEXT = new String[]{"=(", ":(", ":|"};

    public NegativeTextAnalyzer(){
        super(NEGATIVE_TEXT);
    }

    @Override
    public FilterType getFilterType() {
        return FilterType.NEGATIVE_TEXT;
    }
}
