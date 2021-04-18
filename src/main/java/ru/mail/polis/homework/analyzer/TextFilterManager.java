package ru.mail.polis.homework.analyzer;

import java.util.Arrays;

public class TextFilterManager {
    private final TextAnalyzer[] sortedFilters;

    public TextFilterManager(TextAnalyzer[] filters) {
        this.sortedFilters = Arrays.copyOf(filters, filters.length);
        Arrays.sort(sortedFilters, (filter1, filter2) -> {
            if (filter1.priority() < filter2.priority()) {
                return -1;
            } else if (filter1 == filter2) {
                return 0;
            }
            return 1;
        });
    }

    public FilterType analyze(String text) {
        if (text == null || text == "") {
            return FilterType.GOOD;
        }
        for (int i = 0; i < sortedFilters.length; i++) {
            if (sortedFilters[i].check(text)) {
                return sortedFilters[i].filter();
            }
        }
        return FilterType.GOOD;
    }
}
