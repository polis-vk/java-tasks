package ru.mail.polis.homework.analyzer;

public class TooLongAnalyzer implements TextAnalyzer {
    private static final FilterType FILTER_TYPE = FilterType.TOO_LONG;

    private final long maxLength;

    public TooLongAnalyzer(long maxLength) {
        this.maxLength = maxLength;
    }

    @Override
    public FilterType filterType() {
        return FILTER_TYPE;
    }

    @Override
    public boolean analyze(String text) {
        if (text == null || text.isEmpty()) {
            return false;
        }

        return text.length() > maxLength;
    }
}
