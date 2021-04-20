package ru.mail.polis.homework.analyzer.filters;

import ru.mail.polis.homework.analyzer.FilterType;
import ru.mail.polis.homework.analyzer.TextAnalyzer;

public class TooLongFilter implements TextAnalyzer {
    private final static int rank = 2;
    private final long maxLength;

    public TooLongFilter(long maxLengthParam) {
        this.maxLength = maxLengthParam;
    }

    @Override
    public FilterType analyze(String text) {
        if (maxLength < text.length()) {
            return FilterType.TOO_LONG;
        }
        return FilterType.GOOD;
    }

    @Override
    public int getRank() {
        return rank;
    }

}
