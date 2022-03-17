package ru.mail.polis.homework.analyzer;

public class NegativeTextAnalyzer extends SpamTextAnalyzer {

    private static final String[] EMOTION = new String[]{"=(", ":(", ":|"};

    public NegativeTextAnalyzer() {
        super(EMOTION);
    }

    @Override
    public FilterType type(String text) {
        return FilterType.NEGATIVE_TEXT;
    }

    @Override
    public int priorityInfo() {
        return 2;
    }
}

