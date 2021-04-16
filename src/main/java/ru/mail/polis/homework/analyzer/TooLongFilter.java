package ru.mail.polis.homework.analyzer;

public class TooLongFilter implements TextAnalyzer {
    private static final FilterType type = FilterType.TOO_LONG;

    private final long maxLength;

    public TooLongFilter(long maxLength) {
        this.maxLength = maxLength;
    }

    @Override
    public FilterType getType() {
        return type;
    }

    @Override
    public boolean isNotCorrect(String text) {
        if (text == null || text.isEmpty()) {
            return false;
        }

        return text.length() > maxLength;
    }

}
