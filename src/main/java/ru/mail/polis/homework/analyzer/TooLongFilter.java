package ru.mail.polis.homework.analyzer;

public class TooLongFilter implements TextAnalyzer {

    long size;

    public TooLongFilter(long size) {
        this.size = size;
    }

    @Override
    public FilterType analyzer(String str) {
        if (str.length() >= size) {
            return FilterType.TOO_LONG;
        }
        return FilterType.GOOD;
    }
}
