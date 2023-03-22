package ru.mail.polis.homework.analyzer;

public class NegativeTextAnalyzer implements TextAnalyzer {
    final private String[] negativeWords = {"=(", ":(", ":|"};
    static final private FilterType filterType = FilterType.NEGATIVE_TEXT;

    @Override
    public boolean filterWorked(String inputString) {
        for (String word : negativeWords) {
            if (inputString.contains(word)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public FilterType getFilterType() {
        return filterType;
    }
}
