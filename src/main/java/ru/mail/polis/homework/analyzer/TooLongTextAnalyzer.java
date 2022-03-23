package ru.mail.polis.homework.analyzer;

public class TooLongTextAnalyzer implements TextAnalyzer {
    private final long maxLenght;

    TooLongTextAnalyzer(long maxLength) {
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
