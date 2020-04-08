package ru.mail.polis.homework.analyzer.filters;

import ru.mail.polis.homework.analyzer.FilterType;
import ru.mail.polis.homework.analyzer.TextAnalyzer;

public class NegativeTextFilter implements TextAnalyzer {
    private static final String[] TEMPLATES = {"=(", ":(", ":|"};
    private SimpleTextFilter textFilter;

    public NegativeTextFilter() {
        this.textFilter = new SimpleTextFilter(TEMPLATES);
    }

    @Override
    public boolean getResult(String text) {
        return textFilter.analysisText(text);
    }

    @Override
    public FilterType getReturnedValue() {
        return FilterType.NEGATIVE_TEXT;
    }
}
