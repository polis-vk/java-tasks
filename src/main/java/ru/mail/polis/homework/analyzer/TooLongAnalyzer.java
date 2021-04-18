package ru.mail.polis.homework.analyzer;

public class TooLongAnalyzer implements TextAnalyzer {
    private final byte priority = 2;
    private final long maxLength;

    public TooLongAnalyzer(long maxLength) {
        this.maxLength = maxLength;
    }

    public boolean check(String str) {
        return str.length() >= maxLength;
    }

    public FilterType filter() {
        return FilterType.TOO_LONG;
    }

    public byte priority() {
        return priority;
    }
}