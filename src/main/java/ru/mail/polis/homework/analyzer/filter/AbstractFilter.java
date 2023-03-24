package ru.mail.polis.homework.analyzer.filter;

import ru.mail.polis.homework.analyzer.FilterType;
import ru.mail.polis.homework.analyzer.TextAnalyzer;

public abstract class AbstractFilter implements TextAnalyzer {

    private final FilterType filterType;

    public AbstractFilter(FilterType filterType) {
        this.filterType = filterType;
    }

    public FilterType getFilterType() {
        return filterType;
    }

    @Override
    public int compareTo(TextAnalyzer o) {
        return this.filterType.getPriority() - o.getFilterType().getPriority();
    }
}
