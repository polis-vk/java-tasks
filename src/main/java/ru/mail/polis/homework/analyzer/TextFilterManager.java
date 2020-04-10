package ru.mail.polis.homework.analyzer;

import java.util.Arrays;
import java.util.Comparator;

public class TextFilterManager {

    private TextAnalyzer[] allFilters;

    public TextFilterManager(TextAnalyzer[] filters) {
        allFilters = Arrays.copyOf(filters, filters.length);
        Arrays.sort(allFilters, Comparator.comparingInt(this::getPriority));
    }

    public FilterType analyze(String text) {
        if (text == null) {
            return FilterType.GOOD;
        }
        FilterType tmpFilterType;
        //Для работы с каждым элементом массива, нужно использовать цикл for-each
        for (TextAnalyzer analyzer : allFilters) {
            tmpFilterType = analyzer.analyze(text);
            if (tmpFilterType != FilterType.GOOD) {
                return tmpFilterType;
            }
        }
        return FilterType.GOOD;
    }

    private int getPriority(TextAnalyzer analyzer) {
        FilterType tmpFilter = analyzer.getFilterType();
        if (tmpFilter == FilterType.SPAM) {
            return 0;
        } else if (tmpFilter == FilterType.TOO_LONG) {
            return 1;
        } else if (tmpFilter == FilterType.NEGATIVE_TEXT) {
            return 2;
        } else if (tmpFilter == FilterType.CUSTOM) {
            return 3;
        }
        return 4;
    }
}
