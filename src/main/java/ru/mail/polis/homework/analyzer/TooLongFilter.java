package ru.mail.polis.homework.analyzer;

public class TooLongFilter extends Filter {
    long maxLength;

    TooLongFilter(FilterType t, long maxLength) {
        super(t);
        this.maxLength = maxLength;
    }

    @Override
    public FilterType filterText(String text) {
        if (text.length() > maxLength) {
            return type;
        }
        return FilterType.GOOD;
    }
}
