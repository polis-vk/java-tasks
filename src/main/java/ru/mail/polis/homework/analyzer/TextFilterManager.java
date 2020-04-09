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
        //Для работы с каждым элементом массива, нужно использовать цикл for-each
        for (TextAnalyzer analyzer : allFilters) {
            if (analyzer.analyze(text) != FilterType.GOOD) {
                return analyzer.analyze(text);
            }
        }
        return FilterType.GOOD;
    }

    private int getPriority(TextAnalyzer analyzer) {
        if (analyzer.getFilterType() == FilterType.SPAM) {
            return 0;
        } else if (analyzer.getFilterType() == FilterType.TOO_LONG) {
            return 1;
        } else if (analyzer.getFilterType() == FilterType.NEGATIVE_TEXT) {
            return 2;
        } else if (analyzer.getFilterType() == FilterType.CUSTOM) {
            return 3;
        }
        return 4;
    }
}
