package ru.mail.polis.homework.analyzer;

public class NegativeTextAnalyzer implements TextAnalyzer {
    private static final FilterType FILTER_TYPE = FilterType.NEGATIVE_TEXT;
    private static final String[] NEGATIVE_TEXT = {"=(", ":(", ":|"};

    protected static boolean contains(String string, String[] words) {
        for (String word : words) {
            if (string.contains(word)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public FilterType filterType() {
        return FILTER_TYPE;
    }

    @Override
    public boolean analyze(String text) {
        if (text == null || text.isEmpty()) {
            return false;
        }

        return contains(text, NEGATIVE_TEXT);
    }
}
