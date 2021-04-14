package ru.mail.polis.homework.analyzer;

public class NegativeTextAnalyzer implements TextAnalyzer {
    @Override
    public FilterType getType() {
        return FilterType.NEGATIVE_TEXT;
    }

    @Override
    public byte getPriority() {
        return 3;
    }

    @Override
    public boolean isValid(String text) {
        return !(text.contains("=(") || text.contains(":(") || text.contains(":|"));
    }
}
