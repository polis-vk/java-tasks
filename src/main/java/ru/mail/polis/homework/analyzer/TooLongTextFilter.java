package ru.mail.polis.homework.analyzer;

public class TooLongTextFilter implements TextAnalyzer {
    private FilterType typePriority = FilterType.TOO_LONG;
    private long maxLength;

    public TooLongTextFilter(long length) {
        this.maxLength = length;
    }

    @Override
    public FilterType getTypePriority() {
        return typePriority;
    }

    @Override
    public FilterType getTypeOfFilter(String str) {
        if (str.length() > maxLength) {
            return typePriority;
        }
        return FilterType.GOOD;
    }
}
