package ru.mail.polis.homework.analyzer;

public class TooBigDigitAnalyzer implements TextAnalyzer {
    private final int maxDigit;

    public TooBigDigitAnalyzer(int maxDigit) {
        this.maxDigit = maxDigit;
    }

    @Override
    public FilterType filterType() {
        return FilterType.CUSTOM;
    }

    @Override
    public FilterType analyze(String text) {
        for (char ch : text.toCharArray()) {
            if (Character.isDigit(ch) && Character.digit(ch, 10) > maxDigit) {
                return filterType();
            }
        }
        return FilterType.GOOD;
    }
}
