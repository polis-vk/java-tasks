package ru.mail.polis.homework.analyzer;

public class TooLongFilter implements TextAnalyzer {
    private final long maxLength;

    public TooLongFilter(long maxLength) {
        this.maxLength = maxLength;
    }

    @Override
    public FilterType getType() {
        return FilterType.TOO_LONG;
    }

    @Override
    public boolean isNotCorrect(String text) {
        if (text == null) {
            return false;
        }

        return text.length() > maxLength;
    }
}
