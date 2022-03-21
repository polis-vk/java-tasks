package ru.mail.polis.homework.analyzer;

public class NegativeTextAnalyzer extends ForbiddenWords {
    private static final String[] EMOTIONS = new String[]{"=(", ":(", ":|"};

    public NegativeTextAnalyzer(){
        forbiddenWords = EMOTIONS;
        filterType = FilterType.NEGATIVE_TEXT;
    }
}
