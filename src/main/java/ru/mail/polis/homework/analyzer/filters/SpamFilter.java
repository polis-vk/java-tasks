package ru.mail.polis.homework.analyzer.filters;

import ru.mail.polis.homework.analyzer.FilterType;
import ru.mail.polis.homework.analyzer.TextAnalyzer;

public class SpamFilter extends SimpleTextFilter implements TextAnalyzer {

    public SpamFilter(String[] spam) {
        super(spam);
    }

    @Override
    public boolean analysis(String text) {
        return analysisText(text);
    }

    @Override
    public FilterType getFilterType() {
        return FilterType.SPAM;
    }
}
