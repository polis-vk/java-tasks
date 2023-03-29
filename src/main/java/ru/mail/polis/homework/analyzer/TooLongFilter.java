package ru.mail.polis.homework.analyzer;

public class TooLongFilter implements TextAnalyzer {
     private final long maxStrLength;

    TooLongFilter(long maxLength) {
        maxStrLength = maxLength;
    }

    @Override
    public FilterType analyze(String str) {

        if (str == null || str.isEmpty()) {
            return FilterType.GOOD;
        }

        if (str.length() > maxStrLength) {
            return FilterType.TOO_LONG;
        }

        return FilterType.GOOD;
    }

    @Override
    public int getPriority() {
        return FilterType.TOO_LONG.getType();
    }


}
