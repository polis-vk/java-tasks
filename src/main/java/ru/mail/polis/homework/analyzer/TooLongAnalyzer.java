package ru.mail.polis.homework.analyzer;

public class TooLongAnalyzer implements TextAnalyzer {
    long maxLength;
    final static char priority = 1;

    public TooLongAnalyzer(long length) {
        maxLength = length;
    }

    @Override
    public FilterType filtering(String text) {
        if (text.length() > maxLength) {
            return FilterType.TOO_LONG;
        }
        return FilterType.GOOD;
    }

    @Override
    public char getPriority() {
        return TooLongAnalyzer.priority;
    }
}

