package ru.mail.polis.homework.analyzer.filters;

import ru.mail.polis.homework.analyzer.FilterType;
import ru.mail.polis.homework.analyzer.TextAnalyzer;

public class TooLongFilter implements TextAnalyzer {
    private long maxLength;

    public TooLongFilter(long maxLength) {
        this.maxLength = maxLength;
    }

    @Override
    public boolean getResult(String text) {
        return text.length() > maxLength;
    }

    @Override
    public FilterType getReturnedValue() {
        return FilterType.TOO_LONG;
    }
}
