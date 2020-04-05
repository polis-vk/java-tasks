package ru.mail.polis.homework.analyzer.filters;

import ru.mail.polis.homework.analyzer.FilterType;
import ru.mail.polis.homework.analyzer.TextAnalyzer;

public class TooLongFilter implements TextAnalyzer {

    private static final short VALUE = 1;

    private long maxLength;

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
    public short getValue() {
        return VALUE;
    }
}
