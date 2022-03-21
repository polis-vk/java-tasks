package ru.mail.polis.homework.analyzer;

public class NegativeTextAnalyzer implements TextAnalyzer {

    private static final String[] BAD_EMOTIONS = { "=(",":(", ":|" };

    @Override
    public boolean check(String text) {
        for (String badEmotion : BAD_EMOTIONS) {
            if (text.contains(badEmotion)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public FilterType getFilterType() {
        return FilterType.NEGATIVE_TEXT;
    }
}