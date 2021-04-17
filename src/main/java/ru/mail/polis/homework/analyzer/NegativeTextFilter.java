package ru.mail.polis.homework.analyzer;

public class NegativeTextFilter implements TextAnalyzer {
    private static final String[] BAD_EMOTIONS = {"=(", ":(", ":|"};

    @Override
    public FilterType getFilterType() {
        return FilterType.NEGATIVE_TEXT;
    }

    @Override
    public boolean analysis(String text) {
        return textContain(text, BAD_EMOTIONS);
    }
}
