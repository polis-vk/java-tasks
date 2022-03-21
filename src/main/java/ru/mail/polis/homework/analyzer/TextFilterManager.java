package ru.mail.polis.homework.analyzer;

import java.util.Arrays;

public class TextFilterManager {

    private final TextAnalyzer[] filtres;

    public TextFilterManager(TextAnalyzer[] filters) {
        this.filtres = filters.clone();
        Arrays.sort(filtres, (filter1, filter2) -> {
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

        for (TextAnalyzer filter : filtres) {
            if (filter.check(text)) {
                return filter.getFilterType();
            }
        }
        return FilterType.GOOD;
    }
}