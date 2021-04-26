package ru.mail.polis.homework.analyzer.filters;

import ru.mail.polis.homework.analyzer.FilterType;
import ru.mail.polis.homework.analyzer.TextAnalyzer;

public class TooLongFilter implements TextAnalyzer {
    private final long maxLength;

    public TooLongFilter(long maxLengthParam) {
        this.maxLength = maxLengthParam;
    }

    @Override
    public FilterType analyze(String text) {
        if (maxLength < text.length()) {
            return filterType();
        }
        return FilterType.GOOD;
    }

    @Override
    public final FilterType filterType() {
        return FilterType.TOO_LONG;
    }
}
