package ru.mail.polis.homework.analyzer;

public class TooLongTextAnalyzer implements TextAnalyzer {
    long maxLenght;

    public TooLongTextAnalyzer(long maxLenght) {
        this.maxLenght = maxLenght;
    }

    @Override
    public FilterType getFilterType() {
        return FilterType.TOO_LONG;
    }

    @Override
    public boolean check(String text) {
        return text.length() > maxLenght;
    }
}
