package ru.mail.polis.homework.analyzer;

public class NegativeTextAnalyzer implements TextAnalyzer {
    private static final FilterType FILTER_TYPE = FilterType.NEGATIVE_TEXT;

    private static final String[] negativeText = {"=(", ":(", ":|"};

    @Override
    public FilterType filterType() {
        return FILTER_TYPE;
    }

    @Override
    public boolean analyze(String text) {
        if (text == null || text.isEmpty()) {
            return false;
        }

        return Utils.contains(text, negativeText);
    }
}
