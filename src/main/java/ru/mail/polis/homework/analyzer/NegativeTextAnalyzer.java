package ru.mail.polis.homework.analyzer;

public class NegativeTextAnalyzer implements TextAnalyzer {

    private final String[] negativeTexts = {"=(", ":(", ":|"};

    @Override
    public FilterType getType() {
        return FilterType.NEGATIVE_TEXT;
    }

    @Override
    public boolean check(String str) {
        for (String negative: negativeTexts) {
            if (str.contains(negative)) {
                return true;
            }
        }
        return false;
    }
}
