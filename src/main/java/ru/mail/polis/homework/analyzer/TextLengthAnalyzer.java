package ru.mail.polis.homework.analyzer;

public class TextLengthAnalyzer implements TextAnalyzer {
    private final long maxTextLength;

    public TextLengthAnalyzer(long textLength) {
        this.maxTextLength = textLength;
    }

    @Override
    public boolean analyzeText(String text) {
        return text.length() > maxTextLength;
    }

    @Override
    public FilterType getTypeOfFilter() {
        return FilterType.TOO_LONG;
    }

    public long getMaxTextLength() {
        return this.maxTextLength;
    }
}
