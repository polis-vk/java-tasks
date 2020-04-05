package ru.mail.polis.homework.analyzer.filters;

import ru.mail.polis.homework.analyzer.FilterType;
import ru.mail.polis.homework.analyzer.TextAnalyzer;

public class NegativeTextFilter implements TextAnalyzer {

    private static final short VALUE = 2;

    private String[] args = {"=(", ":(", ":|"};

    @Override
    public FilterType analyze(String str) {
        for (String arg : args) {
            if (str.contains(arg)) {
                return FilterType.NEGATIVE_TEXT;
            }
        }
        return null;
    }

    @Override
    public short getValue() {
        return VALUE;
    }
}
