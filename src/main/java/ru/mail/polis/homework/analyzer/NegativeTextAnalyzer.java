package ru.mail.polis.homework.analyzer;

public class NegativeTextAnalyzer implements TextAnalyzer {
    private final String[] negativeSmiles = {"=(", ":(", ":|"};

    @Override
    public boolean analyze(String text) {
        if (text == null) {
            return false;
        }
        for (String negativeSmile : negativeSmiles) {
            if (text.contains(negativeSmile)) {
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
