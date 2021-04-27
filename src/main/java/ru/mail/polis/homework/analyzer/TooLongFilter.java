package ru.mail.polis.homework.analyzer;

public class TooLongFilter implements TextAnalyzer {
    private static final FilterType TYPEOFFILTER = FilterType.TOO_LONG;
    private final long maxLength;

    public TooLongFilter(long maxLength) {
        this.maxLength = maxLength;
    }

    @Override
    public FilterType getType() {
        return TYPEOFFILTER;
    }

    @Override
    public boolean analyze(String text) {
        if (text == null) {
            return false;
        }
        return text.length() > maxLength;
    }
}
