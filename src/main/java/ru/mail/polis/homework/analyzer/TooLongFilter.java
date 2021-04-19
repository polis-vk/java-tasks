package ru.mail.polis.homework.analyzer;

public class TooLongFilter implements TextAnalyzer {
    private final long maxLength;

    public TooLongFilter(long maxLength) {
        this.maxLength = maxLength;
    }

    @Override
    public byte getPriority() {
        return 2;
    }

    @Override
    public FilterType getType() {
        return FilterType.TOO_LONG;
    }

    @Override
    public boolean isNotCorrect(String text) {
        if (text == null || text.isEmpty()) {
            return false;
        }

        return text.length() > maxLength;
    }
}
