package ru.mail.polis.homework.analyzer;

public class PunctuationAnalyzer implements TextAnalyzer {

    private String[] uncorrectCombinations = {"!.", "?.", ",.", "-.", ":.", "!,", "?,", ":,"};

    public boolean isTrue(String text) {
        for (String combination: uncorrectCombinations) {
            if (text.contains(combination)) {
                return true;
            }
        }
        return false;
    }

    public FilterType getFilterType() {
        return FilterType.CUSTOM;
    }
}
