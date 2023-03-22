package ru.mail.polis.homework.analyzer;


public class NegativeTextAnalyzer extends SpamAnalyzer {

    private static final String[] NEGATIVE_EMOTIONS = {"=(", ":(", ":|"};

    public NegativeTextAnalyzer() {
        super(NEGATIVE_EMOTIONS, FilterType.NEGATIVE_TEXT);
    }

}
