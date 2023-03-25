package ru.mail.polis.homework.analyzer;

public class NegativeTextAnalyser implements TextAnalyzer {
    private final static int SIGNIFICANCE = 3;
    private final static String[] NEGATIVE_ELEMENTS = {":(", "=(", ":|"};

    @Override
    public int getSignificance() {
        return SIGNIFICANCE;
    }

    @Override
    public FilterType analyze(String text) {
        for (String negativeElement : NEGATIVE_ELEMENTS) {
            if (text.contains(negativeElement)) {
                return FilterType.NEGATIVE_TEXT;
            }
        }
        return FilterType.GOOD;
    }
}
