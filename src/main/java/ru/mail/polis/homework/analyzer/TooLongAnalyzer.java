package ru.mail.polis.homework.analyzer;

public final class TooLongAnalyzer implements TextAnalyzer {

    private final long maxLength;

    public TooLongAnalyzer(long maxLength) {
        this.maxLength = maxLength;
    }

    @Override
    public FilterType analyze(String text) {
        return isNormal(text) ? FilterType.GOOD : FilterType.TOO_LONG;
    }

    @Override
    public int getPriority() {
        return 1;
    }

    private boolean isNormal(String text) {
        return text != null && text.length() <= maxLength;
    }
}
