package ru.mail.polis.homework.analyzer;

public class TooLongAnalyzer implements TextAnalyzer {
    private final long maxLength;

    public TooLongAnalyzer(long maxLength) {
        this.maxLength = maxLength;
    }

    @Override
    public FilterType getType() {
        return FilterType.TOO_LONG;
    }

    @Override
    public final byte getPriority() {
        return 2;
    }

    @Override
    public boolean isValid(String text) {
        return text.length() <= maxLength;
    }
}
