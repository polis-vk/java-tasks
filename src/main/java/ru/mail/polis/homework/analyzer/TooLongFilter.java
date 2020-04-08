package ru.mail.polis.homework.analyzer;

public class TooLongFilter implements TextAnalyzer {
    protected static int priority;
    private static FilterType filterType = FilterType.TOO_LONG;
    private final long maxLength;

    public TooLongFilter(long maxLength) {
        this.maxLength = maxLength;
    }

    @Override
    public int getFilterPriority() {
        return priority;
    }

    @Override
    public FilterType Analysis(String str) {
        if (str == null || str.isEmpty()) {
            return FilterType.GOOD;
        }

        if (str.length() > maxLength) {
            return filterType;
        }
        return FilterType.GOOD;
    }


}
