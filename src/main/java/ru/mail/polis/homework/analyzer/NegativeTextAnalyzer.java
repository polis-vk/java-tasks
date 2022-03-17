package ru.mail.polis.homework.analyzer;

public class NegativeTextAnalyzer implements TextAnalyzer {

    private static final String[] BAD_EMOTIONS = new String[]{"=(", ":(", ":|"};

    @Override
    public FilterType typeOfType(String text) {
        for (String check : BAD_EMOTIONS) {
            if (text.contains(check)) {
                return FilterType.NEGATIVE_TEXT;
            }
        }
        return FilterType.GOOD;
    }

    @Override
    public int priority() {
        return FilterType.NEGATIVE_TEXT.ordinal();
    }
}
