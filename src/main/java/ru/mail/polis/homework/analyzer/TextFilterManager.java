package ru.mail.polis.homework.analyzer;

import java.util.Arrays;

public class TextFilterManager {

    private final TextAnalyzer[] filters;

    public TextFilterManager(TextAnalyzer[] filters) {
        this.filters = filters.clone();
        Arrays.sort(this.filters, (filter1, filter2) -> {
            if (filter1.getFilterType().getPriority() < filter2.getFilterType().getPriority()) {
                return -1;
            } else if (filter1.getFilterType().getPriority() == filter2.getFilterType().getPriority()) {
                return 0;
            }
            return 1;
        });
    }

    public FilterType analyze(String text) {
        if (text == null || text.isEmpty()) {
            return FilterType.GOOD;
        }

        for (TextAnalyzer filter : filters) {
            if (filter.check(text)) {
                return filter.getFilterType();
            }
        }
        return FilterType.GOOD;
    }
}
