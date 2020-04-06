package ru.mail.polis.homework.analyzer;

public class TooLongFilter implements TextAnalyzer {

    public static long priority;
    private final long maxLength;


    public TooLongFilter(long maxLen) {
        this.maxLength = maxLen;
    }

    @Override
    public long getPriority() {
        return priority;
    }

    @Override
    public FilterType analyze(String str) {
        if (str.length() > this.maxLength) {
            return FilterType.TOO_LONG;
        }
        return null;
    }


}
