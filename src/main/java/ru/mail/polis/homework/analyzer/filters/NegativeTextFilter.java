package ru.mail.polis.homework.analyzer.filters;

import ru.mail.polis.homework.analyzer.FilterType;
import ru.mail.polis.homework.analyzer.TextAnalyzer;

public class NegativeTextFilter extends SimpleTextFilter implements TextAnalyzer {
    private static final String[] TEMPLATES = {"=(", ":(", ":|"};

    public NegativeTextFilter() {
        super(TEMPLATES);
    }

    @Override
    public boolean analysis(String text) {
        return analysisText(text);
    }

    @Override
    public FilterType getFilterType() {
        return FilterType.NEGATIVE_TEXT;
    }
}
