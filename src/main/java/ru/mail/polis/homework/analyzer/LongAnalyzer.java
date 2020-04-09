package ru.mail.polis.homework.analyzer;

public class LongAnalyzer implements TextAnalyzer {
    private long stringLenght;

    public LongAnalyzer(long length) {
        stringLenght = length;
    }

    @Override
    public FilterType analyze(String txt) {
        if (txt.length() > stringLenght) {
            return FilterType.TOO_LONG;
        }
        return FilterType.GOOD;
    }

    @Override
    public FilterType getFilterType() {
        return FilterType.TOO_LONG;
    }
}
