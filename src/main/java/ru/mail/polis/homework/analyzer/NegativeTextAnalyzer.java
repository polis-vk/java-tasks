package ru.mail.polis.homework.analyzer;


public class NegativeTextAnalyzer implements TextAnalyzer {
    private final String[] negativeWords = new String[]{"=(", ":(", ":|"};

    public NegativeTextAnalyzer() {
        super();
    }

    @Override
    public FilterType getFilterType() {
        return FilterType.NEGATIVE_TEXT;
    }

    @Override
    public boolean isMatchFilter(String text) {
        for (String word : negativeWords) {
            if (text.contains(word)) {
                return true;
            }
        }
        return false;
    }
}
