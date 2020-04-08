package ru.mail.polis.homework.analyzer;

public class NegativeTextFilter implements TextAnalyzer {

    private static final FilterType type = FilterType.NEGATIVE_TEXT;
    private static final String[] negativeSmiles = {"=(", ":(", ":|"};


    @Override
    public FilterType analyze(String text) {
        for (String negativeText : negativeSmiles) {
            if (text.contains(negativeText)) {
                return type;
            }
        }
        return good;
    }
}
