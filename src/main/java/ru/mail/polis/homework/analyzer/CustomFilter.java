package ru.mail.polis.homework.analyzer;

public class CustomFilter implements TextAnalyzer {
    private final char symbol;
    private final int count;

    public CustomFilter(char symbol, int count) {
        this.symbol = symbol;
        this.count = count;
    }

    @Override
    public FilterType getType() {
        return FilterType.CUSTOM;
    }

    @Override
    public boolean isNotCorrect(String text) {
        if (text == null) {
            return false;
        }

        byte tempCount = 0;
        for (char ch : text.toCharArray()) {
            if (ch == symbol) {
                tempCount++;
            }
        }
        return tempCount >= count;
    }
}
