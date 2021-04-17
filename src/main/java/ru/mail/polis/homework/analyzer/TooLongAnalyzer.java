package ru.mail.polis.homework.analyzer;

public class TooLongAnalyzer implements TextAnalyzer {
    private final long MAX_LENGTH;

    public TooLongAnalyzer(long maxLength) {
        this.MAX_LENGTH = maxLength;
    }


    @Override
    public FilterType getTypeFilter() {
        return FilterType.TOO_LONG;
    }

    @Override
    public boolean isCorrect(String text) {
        return text == null || text.isEmpty() || (text.length() <= MAX_LENGTH);
    }
}
