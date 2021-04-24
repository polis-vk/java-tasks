package ru.mail.polis.homework.analyzer;

public class NegativeTextAnalyzer implements TextAnalyzer {
    private final static String[] BAD_EMOTIONS = {"=(", ":(", ":|"};

    public NegativeTextAnalyzer() {
    }

    @Override
    public FilterType analyze(String text) {
        for (String emotion : BAD_EMOTIONS) {
            if (text.contains(emotion)) {
                return FilterType.NEGATIVE_TEXT;
            }
        }
        return FilterType.GOOD;
    }

    @Override
    public FilterType getType() {
        return FilterType.NEGATIVE_TEXT;
    }
}
