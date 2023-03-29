package ru.mail.polis.homework.analyzer;

public class TooLongAnalyzer implements TextAnalyzer {
    private final long length;
    private FilterType type;

    TooLongAnalyzer(long maxLength) {
        this.length = maxLength;
    }

    public void setType() {
        this.type = FilterType.TOO_LONG;
    }

    @Override
    public FilterType getType() {
        return this.type;
    }

    @Override
    public boolean filterWorked(String str) {
        return this.length <= str.length();
    }
}

