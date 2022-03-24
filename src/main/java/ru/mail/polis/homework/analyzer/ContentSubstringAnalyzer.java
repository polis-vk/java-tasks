package ru.mail.polis.homework.analyzer;

import java.util.Arrays;

public class ContentSubstringAnalyzer implements TextAnalyzer {
    public String[] desiredContainedSubstrings;
    public final FilterType filterType;

    ContentSubstringAnalyzer(String[] desiredContainedSubstrings, FilterType filterType) {
        this.desiredContainedSubstrings = Arrays.copyOf(desiredContainedSubstrings, desiredContainedSubstrings.length);
        this.filterType = filterType;
    }

    @Override
    public boolean check(String text) {
        for (String s : desiredContainedSubstrings) {
            if (text.contains(s)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public FilterType getFilterType() {
        return this.filterType;
    }
}
