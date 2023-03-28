package ru.mail.polis.homework.analyzer;

/**
 * Количество цифр в строке не должно превышать amountOfNumbers
 */
public class CustomAnalyzer implements TextAnalyzer {
    private final int amountOfNumbers;

    public CustomAnalyzer(Integer amountOfNumbers) {
        this.amountOfNumbers = amountOfNumbers;
    }

    @Override
    public FilterType getType() {
        return FilterType.CUSTOM;
    }

    @Override
    public boolean makeAnalysis(String text) {
        int countNumbers = 0;
        for (int i = 0; i < text.length(); i++) {
            if (Character.isDigit(text.charAt(i))) {
                countNumbers++;
            }
        }
        return countNumbers <= amountOfNumbers;
    }
}
