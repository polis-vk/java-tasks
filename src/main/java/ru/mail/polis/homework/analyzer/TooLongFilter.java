package ru.mail.polis.homework.analyzer;

public class TooLongFilter implements TextAnalyzer {
    long maxLength;

    public TooLongFilter(long maxLength) {
        this.maxLength = maxLength;
    }

    @Override
    public boolean filterText(String text) {
        return text.length() > maxLength;
    }

    @Override
    public FilterType getType() {
        return FilterType.TOO_LONG;
    }
}
