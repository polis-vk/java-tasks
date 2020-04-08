package ru.mail.polis.homework.analyzer;

import java.util.Arrays;

public class NegativeFilter implements TextAnalyzer {
    protected static int priority;
    private static FilterType filterType = FilterType.NEGATIVE_TEXT;
    private static final String[] negative = {"=(", ":(", ":|"};


    @Override
    public int getFilterPriority() {
        return priority;
    }

    @Override
    public FilterType Analysis(String str) {
        if (str == null || str.isEmpty()) {
            return FilterType.GOOD;
        }

        for (String negativeWord : negative) {
            if (str.contains(negativeWord)) {
                return filterType;
            }
        }
        return FilterType.GOOD;
    }


}
