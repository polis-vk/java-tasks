package ru.mail.polis.homework.analyzer;

public class NegativeTextAnalyzer extends InclusionFinder implements TextAnalyzer {

    private final static String[] NEGATIVE_TEXT = {"=(", ":(", ":|"};

    @Override
    public boolean checkCorrection(String text) {
        return checkInclusion(text, NEGATIVE_TEXT);
    }

    @Override
    public FilterType getFilterType() {
        return FilterType.NEGATIVE_TEXT;
    }
}