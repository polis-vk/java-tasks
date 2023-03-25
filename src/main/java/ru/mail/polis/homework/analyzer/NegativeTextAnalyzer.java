package ru.mail.polis.homework.analyzer;

public class NegativeTextAnalyzer implements TextAnalyzer {
    private final String[] negative = {"=(", ":(", ":|"};

    @Override
    public boolean analyze(String text) {
        for (String negatives : negative) {
            int indexSpam = text.indexOf(negatives);
            if (indexSpam != -1) {
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
