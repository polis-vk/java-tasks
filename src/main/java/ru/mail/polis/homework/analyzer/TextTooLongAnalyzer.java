package ru.mail.polis.homework.analyzer;

public class TextTooLongAnalyzer implements TextAnalyzer {

    private final long maxLength;

    public TextTooLongAnalyzer(long maxLength) {
        this.maxLength = maxLength;
    }

    @Override
    public boolean analyze(String text) {
        return text.length() > maxLength;
    }

    @Override
    public FilterType getType() {
        return FilterType.TOO_LONG;
    }
}
