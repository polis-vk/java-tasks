package ru.mail.polis.homework.analyzer;

public class TooLongAnalyzer implements TextAnalyzer {
    private byte priority = 2;
    private long maxLength;

    public TooLongAnalyzer(long maxLength) {
        this.maxLength = maxLength;
    }

    public byte priority() {
        return priority;
    }

    public boolean check(String str) {
        return str.length() >= maxLength ? true : false;
    }

    public FilterType filter() {
        return FilterType.TOO_LONG;
    }
}