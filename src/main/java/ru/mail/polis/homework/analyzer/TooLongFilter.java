package ru.mail.polis.homework.analyzer;

public class TooLongFilter implements TextAnalyzer {
    private static final FilterType FILTER_TYPE = FilterType.TOO_LONG;
    private final long MAX_LENGTH;

    public TooLongFilter(long maxLength) {
        this.MAX_LENGTH = maxLength;
    }

    @Override
    public int getFilterPriority() {
        return FILTER_TYPE.getPriority();
    }

    @Override
    public FilterType analysis(String str) {
        if (str == null || str.isEmpty()) {
            return FilterType.GOOD;
        }

        if (str.length() > MAX_LENGTH) {
            return FILTER_TYPE;
        }
        return FilterType.GOOD;
    }


}
