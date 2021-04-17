package ru.mail.polis.homework.analyzer;

public class WrongStartFilter implements TextAnalyzer {
    private static final String[] INCORRECT_BEGINNING = {"Ü", "Ú", "Û"};

    @Override
    public FilterType getFilterType() {
        return FilterType.CUSTOM;
    }

    @Override
    public boolean analysis(String text) {
        for (String word : text.toUpperCase().split(" ")) {
            for (String beginning : INCORRECT_BEGINNING) {
                if (word.length() > 1 && word.startsWith(beginning)) {
                    return true;
                }
            }
        }
        return false;
    }
}
