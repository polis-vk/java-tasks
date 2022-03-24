package ru.mail.polis.homework.analyzer;

public class NegativeTextAnalyzer implements TextAnalyzer {

    private final String[] NEGATIVE_TEXT = {"=(", ":(", ":|"};

    @Override
    public boolean examine(String text){

        for (String negWord : NEGATIVE_TEXT) {
            if (text.contains(negWord)) {
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
