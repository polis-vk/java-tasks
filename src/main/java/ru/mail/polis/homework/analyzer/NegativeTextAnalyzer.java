package ru.mail.polis.homework.analyzer;

public class NegativeTextAnalyzer extends ForbiddenTextAnalyzer {
    private static final String[] NEGATIVE_EMOTIONS = new String[]{"=(", ":(", ":|"};

    public NegativeTextAnalyzer() {
        forbiddenText = NEGATIVE_EMOTIONS;
        filterType = FilterType.NEGATIVE_TEXT;
    }
}
