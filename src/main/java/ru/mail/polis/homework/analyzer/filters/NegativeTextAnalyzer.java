package ru.mail.polis.homework.analyzer.filters;

import ru.mail.polis.homework.analyzer.FilterType;

public class NegativeTextAnalyzer extends Filter {

    private final String[] negativeWords = {"=(", ":(", ":|"};

    @Override
    public boolean check(String text) {

        if (text == null) {
            return false;
        }

        for (String word : negativeWords) {
            if (text.contains(word)) {
                return true;
            }
        }

        return false;
    }

    @Override
    public FilterType getFilterType() {
        return FilterType.NEGATIVE_TEXT;
    }

    public NegativeTextAnalyzer() {
        order = 3;
    }
}
