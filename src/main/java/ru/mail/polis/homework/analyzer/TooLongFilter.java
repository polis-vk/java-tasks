package ru.mail.polis.homework.analyzer;

public class TooLongFilter implements TextAnalyzer {

    long maxLength;

    public TooLongFilter(long maxLen) {
        this.maxLength = maxLen;
    }

    @Override
    public FilterType analyze(String str) {
        if (str.length() > this.maxLength) {
            return FilterType.TOO_LONG;
        }
        return null;
    }

    @Override
    public FilterType getPriority() {
        return FilterType.TOO_LONG;
    }
}
