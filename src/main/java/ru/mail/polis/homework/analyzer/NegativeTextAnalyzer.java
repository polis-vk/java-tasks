package ru.mail.polis.homework.analyzer;

public class NegativeTextAnalyzer implements TextAnalyzer, TextScanning {
    private final String[] badEmotions = new String[]{"=(", ":(", ":|"};

    @Override
    public boolean isNotCorrectString(String str) {
        return containsBadSymbols(str, badEmotions);
    }

    @Override
    public FilterType getType() {
        return FilterType.NEGATIVE_TEXT;
    }
}
