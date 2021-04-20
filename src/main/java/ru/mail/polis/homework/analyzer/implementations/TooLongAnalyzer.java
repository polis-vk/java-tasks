package ru.mail.polis.homework.analyzer.implementations;

import ru.mail.polis.homework.analyzer.FilterType;
import ru.mail.polis.homework.analyzer.TextAnalyzer;

public final class TooLongAnalyzer implements TextAnalyzer {

    private final long maxSize;
    private final FilterType filterType = FilterType.TOO_LONG;

    public TooLongAnalyzer(long maxSize) {
        this.maxSize = maxSize;
    }

    @Override
    public FilterType process(String text) {
        if (text.length() > maxSize) {
            return getLabel();
        }
        return FilterType.GOOD;
    }

    @Override
    public FilterType getLabel() {
        return filterType;
    }
}
