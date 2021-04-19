package ru.mail.polis.homework.analyzer;

public class TooLongFilter implements TextAnalyzer {
    final int PRIORITY = 2;
    private static final FilterType typeOfFilter = FilterType.TOO_LONG;
    private final long maxLength;

    public TooLongFilter(long maxLength) {
        this.maxLength = maxLength;
    }

    @Override
    public FilterType getType() {
        return typeOfFilter;
    }

    @Override
    public int getPriority() {
        return PRIORITY;
    }

    @Override
    public boolean analyze(String text) {
        if (text == null) {
            return false;
        }
        return text.length() > maxLength;
    }
}
