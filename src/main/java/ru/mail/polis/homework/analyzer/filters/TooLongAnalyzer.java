package ru.mail.polis.homework.analyzer.filters;

import ru.mail.polis.homework.analyzer.FilterType;

public class TooLongAnalyzer extends Filter {

    private long maxLength;

    @Override
    public boolean check(String text) {

        if (text == null) {
            return false;
        }

        if (text.length() > maxLength) {
            return true;
        }

        return false;
    }

    @Override
    public FilterType getFilterType() {
        return FilterType.TOO_LONG;
    }

    public TooLongAnalyzer(long max) {
        maxLength = max;
        order = 2;
    }
}
