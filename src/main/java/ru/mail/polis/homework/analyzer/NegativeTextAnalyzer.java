package ru.mail.polis.homework.analyzer;

public class NegativeTextAnalyzer extends SpamAnalyzer {

    private static final String[] BAD_EMOTIONS = {"=(",":(", ":|"};

    NegativeTextAnalyzer(){
        super(BAD_EMOTIONS);
    }

    @Override
    public FilterType getFilterType() {
        return FilterType.NEGATIVE_TEXT;
    }
}
