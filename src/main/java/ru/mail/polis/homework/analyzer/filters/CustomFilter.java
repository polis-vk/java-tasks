package ru.mail.polis.homework.analyzer.filters;

import ru.mail.polis.homework.analyzer.FilterType;
import ru.mail.polis.homework.analyzer.TextAnalyzer;

public class CustomFilter extends Search implements TextAnalyzer {
    @Override
    public FilterType analyze(String str) {
        return check(str, new String[]{"Putin"}, FilterType.CUSTOM);
    }

    @Override
    public FilterType getType() {
        return FilterType.CUSTOM;
    }
}
