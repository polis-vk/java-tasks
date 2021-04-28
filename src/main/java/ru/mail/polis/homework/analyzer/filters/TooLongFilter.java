package ru.mail.polis.homework.analyzer.filters;

import ru.mail.polis.homework.analyzer.TextAnalyzer;
import ru.mail.polis.homework.analyzer.FilterType;

public class TooLongFilter implements TextAnalyzer {
    private final long MAX_LENGTH;
    private static final int PRIORITY = 1;

    public TooLongFilter(long maxLength) {
        this.MAX_LENGTH = maxLength;
    }

    @Override
    public int getPriority() {
        return PRIORITY;
    }

    @Override
    public FilterType analyze(String text) {
        if ((MAX_LENGTH < text.length()))  {
            return FilterType.TOO_LONG;
        }
        return FilterType.GOOD;
    }



}
