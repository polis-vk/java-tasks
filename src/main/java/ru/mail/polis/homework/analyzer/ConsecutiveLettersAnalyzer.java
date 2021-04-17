package ru.mail.polis.homework.analyzer;


public class ConsecutiveLettersAnalyzer implements TextAnalyzer {
    private final String compareLetters;

    public ConsecutiveLettersAnalyzer(char letter, int minCount) {
        compareLetters = String.valueOf(letter).repeat(Math.max(0, minCount));
    }

    @Override
    public FilterType getTypeFilter() {
        return FilterType.CONSECUTIVE_LETTERS;
    }

    @Override
    public boolean isCorrect(String text) {
        if (text == null || text.isEmpty()) {
            return true;
        }

        return !text.contains(compareLetters);
    }
}
