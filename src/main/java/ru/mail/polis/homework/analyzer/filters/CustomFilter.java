package ru.mail.polis.homework.analyzer.filters;

import ru.mail.polis.homework.analyzer.FilterType;
import ru.mail.polis.homework.analyzer.TextAnalyzer;

public class CustomFilter implements TextAnalyzer {

    private static final FilterType VALUE = FilterType.CUSTOM;

    @Override
    public FilterType analyze(String str) {
        String arg = "Putin";
        if (str.equals(arg)) {
            return FilterType.CUSTOM;
        }
        return null;
    }

    @Override
    public FilterType getValue() {
        return VALUE;
    }
}
