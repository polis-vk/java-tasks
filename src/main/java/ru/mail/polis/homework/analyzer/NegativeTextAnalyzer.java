package ru.mail.polis.homework.analyzer;

public class NegativeTextAnalyzer implements TextAnalyzer {
    @Override
    public boolean isNotCorrectString(String str) {
        return str.contains("=(") || str.contains(":(") || str.contains(":|");
    }

    @Override
    public int getPriority() {
        return 1;
    }

    @Override
    public FilterType getType() {
        return FilterType.NEGATIVE_TEXT;
    }
}
