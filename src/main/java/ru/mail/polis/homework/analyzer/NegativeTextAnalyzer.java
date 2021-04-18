package ru.mail.polis.homework.analyzer;

public class NegativeTextAnalyzer implements TextAnalyzer {
    private final String[] badEmotions = new String[]{"=(", ":(", ":|"};
    private final int priority = 1;

    @Override
    public boolean isNotCorrectString(String str) {
        return containsBadSymbols(str, badEmotions);
    }

    @Override
    public int getPriority() {
        return priority;
    }

    @Override
    public FilterType getType() {
        return FilterType.NEGATIVE_TEXT;
    }
}
