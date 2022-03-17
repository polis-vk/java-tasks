package ru.mail.polis.homework.analyzer;

public class NegativeTextAnalyzer implements TextAnalyzer {
    private String[] banSym = {"=(", ":(", ":|"};

    @Override
    public FilterType FilterValue(String text) {
        for (int i = 0; i < banSym.length; i++) {
            if (text.contains(banSym[i])) {
                return FilterType.NEGATIVE_TEXT;
            }
        }
        return FilterType.GOOD;
    }
}
