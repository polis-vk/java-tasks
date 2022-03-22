package ru.mail.polis.homework.analyzer;

public class TooLongAnalyzer implements TextAnalyzer {
    private final long length;

    public TooLongAnalyzer(long maxLength) {
        this.length = maxLength;
    }

    @Override
    public boolean analyze(String text) {
        return text.length() >= length;
    }

    public FilterType getType() {
        return FilterType.TOO_LONG;
    }
}
