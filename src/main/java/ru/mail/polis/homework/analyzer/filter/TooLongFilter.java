package ru.mail.polis.homework.analyzer.filter;

import ru.mail.polis.homework.analyzer.FilterType;

public class TooLongFilter extends AbstractFilter {

    private final long maxLength;

    public TooLongFilter(long maxLength) {
        super(FilterType.TOO_LONG);
        this.maxLength = maxLength;
    }

    public long getMaxLength() {
        return maxLength;
    }

    @Override
    public boolean applies(String text) {
        if (text == null) {
            return false;
        }
        return (text.length() > maxLength);
    }
}
