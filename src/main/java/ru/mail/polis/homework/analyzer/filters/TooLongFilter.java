package ru.mail.polis.homework.analyzer.filters;

import ru.mail.polis.homework.analyzer.FilterType;
import ru.mail.polis.homework.analyzer.TextAnalyzer;

public class TooLongFilter implements TextAnalyzer {

    private final long maxLength;
    private final FilterType longFilterType = FilterType.TOO_LONG;

    public TooLongFilter(long maxLength) {
        this.maxLength = maxLength;
    }

    @Override
    public FilterType analyze(String text) {
        if (text.length() > maxLength) {
            return  longFilterType;
        }
        return FilterType.GOOD;
    }

    @Override
    public FilterType getType() {
        return longFilterType;
    }
}
