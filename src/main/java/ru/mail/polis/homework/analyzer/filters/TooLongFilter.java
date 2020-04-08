package ru.mail.polis.homework.analyzer.filters;

import ru.mail.polis.homework.analyzer.FilterType;
import ru.mail.polis.homework.analyzer.TextAnalyzer;

public class TooLongFilter implements TextAnalyzer {

    private static final FilterType VALUE = FilterType.TOO_LONG;

    private final long maxLength;

    public TooLongFilter(long maxLength) {
        this.maxLength = maxLength;
    }

    @Override
    public FilterType analyze(String str) {
        if (str.length() > maxLength) {
            return FilterType.TOO_LONG;
        }
        return null;
    }

    @Override
    public FilterType getValue() {
        return VALUE;
    }
}
