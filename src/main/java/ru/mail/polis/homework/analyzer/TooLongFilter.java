package ru.mail.polis.homework.analyzer;

public class TooLongFilter implements TextAnalyzer {

    private final long maxLength;

    public TooLongFilter(long maxLength) {
        this.maxLength = maxLength;
    }

    @Override
    public int getFilterPriority() {
        return FilterType.TOO_LONG.getPriority();
    }

    @Override
    public FilterType analysis(String str) {
        if (str == null || str.isEmpty()) {
            return FilterType.GOOD;
        }

        if (str.length() > maxLength) {
            return FilterType.TOO_LONG;
        }
        return FilterType.GOOD;
    }
}
