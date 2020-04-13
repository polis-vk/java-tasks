package ru.mail.polis.homework.analyzer.filters;

import ru.mail.polis.homework.analyzer.FilterType;
import ru.mail.polis.homework.analyzer.TextAnalyzer;

public class NegativeTextFilter extends Search implements TextAnalyzer {
    private static final String[] ARGS = {"=(", ":(", ":|"};

    @Override
    public FilterType analyze(String str) {
        return check(str, ARGS, FilterType.NEGATIVE_TEXT);
    }

    @Override
    public FilterType getType() {
        return FilterType.NEGATIVE_TEXT;
    }
}
