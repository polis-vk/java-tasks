package ru.mail.polis.homework.analyzer;

public class NumbersAnalyzer implements TextAnalyzer {
    private final String[] numbers = new String[]{"0", "1", "2", "3", "4", "5", "6", "7", "8", "9"};
    private final int priority = 0;

    @Override
    public boolean isNotCorrectString(String str) {
        return containsBadSymbols(str, numbers);
    }

    @Override
    public int getPriority() {
        return priority;
    }

    @Override
    public FilterType getType() {
        return FilterType.NUMBERS;
    }
}
