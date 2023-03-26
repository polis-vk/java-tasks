package ru.mail.polis.homework.analyzer;

public class NegativeTextAnalyzer implements TextAnalyzer {
    private final String[] smilies = {"=(", ":(", ":|"};

    @Override
    public int getPriority() {
        return 3;
    }

    @Override
    public FilterType getFilterType(String str) {
        for (String smile : smilies) {
            if (str.contains(smile)) {
                return FilterType.NEGATIVE_TEXT;
            }
        }
        return FilterType.GOOD;
    }

}
