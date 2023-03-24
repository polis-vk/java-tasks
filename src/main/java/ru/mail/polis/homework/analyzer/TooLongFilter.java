package ru.mail.polis.homework.analyzer;

public class TooLongFilter implements TextAnalyzer {
    final long TOO_LONG;

    TooLongFilter(long maxLength) {
        TOO_LONG = maxLength;
    }

    @Override
    public FilterType analyze(String str) {

        if (str == null || str.isEmpty()) {
            return FilterType.GOOD;
        }

        if (str.length() > TOO_LONG) {
            return FilterType.TOO_LONG;
        } else {
            return FilterType.GOOD;
        }
    }


}
