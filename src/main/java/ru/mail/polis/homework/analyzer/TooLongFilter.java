package ru.mail.polis.homework.analyzer;

public class TooLongFilter implements TextAnalyzer {
    static final FilterType FILTER_TYPE = FilterType.TOO_LONG;
    private final long maxLength;

    public TooLongFilter(long maxLength) {
        this.maxLength = maxLength;
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

        if (str.length() > maxLength) {
            return FILTER_TYPE;
        }
        return FilterType.GOOD;
    }


}
