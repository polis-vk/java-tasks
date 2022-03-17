package ru.mail.polis.homework.analyzer;

public class TooLongTextAnalyzer implements TextAnalyzer {

    private final long maxLength;
    public TooLongTextAnalyzer(long maxLength) {
        this.maxLength = maxLength;
    }

    @Override
    public FilterType filterType(String text) {
        if (text.length() > maxLength) {
            return FilterType.TOO_LONG;
        }
        return FilterType.GOOD;
    }

    @Override
    public FilterType type(String text) {
        return FilterType.TOO_LONG;
    }

    @Override
    public int priorityInfo() {
        return 1;
    }
}
