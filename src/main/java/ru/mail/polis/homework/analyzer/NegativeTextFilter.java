package ru.mail.polis.homework.analyzer;

public class NegativeTextFilter implements TextAnalyzer {
    @Override
    public FilterType analyze(String str) {
        if (str == null || str.isEmpty()) {
            return FilterType.GOOD;
        }
        if (str.contains("=(") || str.contains(":(") || str.contains(":|")) {
            return FilterType.NEGATIVE_TEXT;
        }
        return FilterType.GOOD;
    }
}
