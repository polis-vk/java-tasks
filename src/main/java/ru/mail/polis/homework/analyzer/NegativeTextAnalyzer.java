package ru.mail.polis.homework.analyzer;

public class NegativeTextAnalyzer implements TextAnalyzer {
    private final static String[] NEGATIVE_ELEMENTS = {":(", "=(", ":|"};

    @Override
    public FilterType getType() {
        return FilterType.NEGATIVE_TEXT;
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
