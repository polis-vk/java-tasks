package ru.mail.polis.homework.analyzer;

public class NegativeAnalyzer implements TextAnalyzer {
    private final String[] emotions = {"=(", ":(", ":|"};

    @Override
    public boolean isAnalyze(String str) {
        for (String emotion : emotions) {
            if (str.contains(emotion)) {
                return false;
            }
        }
        return true;
    }

    @Override
    public FilterType getType() {
        return FilterType.NEGATIVE_TEXT;
    }
}
