package ru.mail.polis.homework.analyzer;

public class NegativeTextAnalyzer extends ForbiddenConstructionsAnalyzer {
    private static final String[] NEGATIVE_EMOTIONS = new String[]{"=(", ":(", ":|"};

    public NegativeTextAnalyzer() {
        super(NEGATIVE_EMOTIONS, FilterType.NEGATIVE_TEXT);
    }
}
