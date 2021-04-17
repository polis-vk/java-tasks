package ru.mail.polis.homework.analyzer;


public class ConsecutiveLettersAnalyzer implements TextAnalyzer {
    private final String COMPARE_LETTERS;

    public ConsecutiveLettersAnalyzer(char letter, int minCount) {
        COMPARE_LETTERS = String.valueOf(letter).repeat(Math.max(0, minCount));
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

        return !text.contains(COMPARE_LETTERS);
    }
}
