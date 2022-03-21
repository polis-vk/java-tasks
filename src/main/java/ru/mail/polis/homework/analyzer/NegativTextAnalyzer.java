package ru.mail.polis.homework.analyzer;

public class NegativTextAnalyzer extends ForbiddenTextAnalyzer {
    private static final String[] NEGATIVE_EMOTIONS = new String[]{"=(", ":(", ":|"};

    public NegativTextAnalyzer() {
        forbiddenText = NEGATIVE_EMOTIONS;
        filterType = FilterType.NEGATIVE_TEXT;
    }
}
