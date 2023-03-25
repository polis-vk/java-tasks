package ru.mail.polis.homework.analyzer;

public class NegativeTextAnalyzer implements TextAnalyzer {


    private final String[] negatives = {"=(", ":(", ":|"};

    @Override
    public FilterType analyze(String str) {
        for (String words : this.negatives) {
            if (str.contains(words)) {
                return FilterType.NEGATIVE_TEXT;
            }
        }
        return FilterType.GOOD;
    }


}
