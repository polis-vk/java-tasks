package ru.mail.polis.homework.analyzer;

public class TooLongAnalyzer implements TextAnalyzer{

    long maxLength;

    public TooLongAnalyzer(long maxLength) {
        this.maxLength = maxLength;
    }

    @Override
    public FilterType getType() {
        return FilterType.TOO_LONG;
    }

    @Override
    public boolean check(String str) {
        return str.length() > this.maxLength;
    }
}
