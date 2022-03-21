package ru.mail.polis.homework.analyzer;

import java.util.Arrays;

public class ForbiddenConstructionsAnalyzer implements TextAnalyzer {
    final String[] forbiddenConstructions;
    final FilterType filterType;

    protected ForbiddenConstructionsAnalyzer(String[] forbiddenConstructions, FilterType filterType) {
        this.forbiddenConstructions = Arrays.copyOf(forbiddenConstructions, forbiddenConstructions.length);
        this.filterType = filterType;
    }

    @Override
    public boolean analyze(String text) {
        for (String word : forbiddenConstructions) {
            if (text.contains(word)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public FilterType getFilterType() {
        return filterType;
    }
}
