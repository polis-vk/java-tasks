package ru.mail.polis.homework.analyzer.filters;

import ru.mail.polis.homework.analyzer.FilterType;
import ru.mail.polis.homework.analyzer.TextAnalyzer;

public class SpamFilter implements TextAnalyzer {
    private SimpleTextFilter textFilter;

    public SpamFilter(String[] spam) {
        textFilter = new SimpleTextFilter(spam);
    }

    @Override
    public boolean getResult(String text) {
        return textFilter.analysisText(text);
    }

    @Override
    public FilterType getReturnedValue() {
        return FilterType.SPAM;
    }
}
