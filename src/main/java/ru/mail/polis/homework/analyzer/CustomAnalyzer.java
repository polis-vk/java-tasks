package ru.mail.polis.homework.analyzer;

public class CustomAnalyzer implements TextAnalyzer {
    // количество цифр в строке не должно превышать amountOfNumbers
    private final FilterType filterType;
    private final Integer amountOfNumbers;

    public CustomAnalyzer(Integer amountOfNumbers) {
        this.amountOfNumbers = amountOfNumbers;
        this.filterType = FilterType.CUSTOM;
    }

    @Override
    public FilterType getType() {
        return this.filterType;
    }

    @Override
    public FilterType makeAnalysis(String text) {
        int countNumbers = 0;
        for (int i = 0; i < text.length(); i++) {
            if (Character.isDigit(text.charAt(i))) {
                countNumbers++;
            }
        }
        if (countNumbers > amountOfNumbers) {
            return this.filterType;
        }
        return FilterType.GOOD;
    }
}
