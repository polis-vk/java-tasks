package ru.mail.polis.homework.analyzer;

public class TooLongAnalyzer implements TextAnalyzer {
    private final long maxLength;
    private final static FilterType FILTER_TYPE = FilterType.TOO_LONG;

    public TooLongAnalyzer(long maxLength) {
        this.maxLength = maxLength;
    }

    @Override
    public boolean analyze(String text) {
        return text.length() > maxLength;
    }

    @Override
    public FilterType getAnalyzerType() {
        return FILTER_TYPE;
    }
}
