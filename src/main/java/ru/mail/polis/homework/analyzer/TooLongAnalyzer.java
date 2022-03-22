package ru.mail.polis.homework.analyzer;

public class TooLongAnalyzer implements TextAnalyzer {
    long maxLenght;

    TooLongAnalyzer(long maxLength) {
        this.maxLenght = maxLength;
    }

    @Override
    public FilterType FilterValue(String text) {
        if (text.length() > maxLenght) {
            return FilterType.TOO_LONG;
        }
        return FilterType.GOOD;
    }
}
