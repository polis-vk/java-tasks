package ru.mail.polis.homework.analyzer;

public class TooShortTextAnalyzer implements TextAnalyzer {
    private final int minLength;

    public TooShortTextAnalyzer(int minLength) {
        this.minLength = minLength;
    }

    @Override
    public boolean isFilterWorked(String text) {
        String[] word = text.split("[ ,.\"/;:)=(|!<>]+");
        for (String words : word) {
            if (words.length() < minLength) {
                return true;
            }
        }
        return false;
    }

    @Override
    public FilterType getFilterType() {
        return FilterType.CUSTOM;
    }
}
