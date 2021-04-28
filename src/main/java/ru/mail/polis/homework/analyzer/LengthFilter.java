package ru.mail.polis.homework.analyzer;

public class LengthFilter implements TextAnalyzer {
    private final long maxLength;

    public LengthFilter(long maxLength) {
        this.maxLength = maxLength;
    }

    public FilterType type() {
        return FilterType.TOO_LONG;
    }

    public boolean isValid(String text) {
        return text == null || text.isEmpty() || text.length() <= this.maxLength;
    }
}

