package ru.mail.polis.homework.analyzer;

public class TooLongAnalyzer implements TextAnalyzer {
    private final long LENGTH;

    public TooLongAnalyzer(long length) {
        this.LENGTH = length;
    }

    @Override
    public FilterType getFilterType() {
        return FilterType.TOO_LONG;
    }

    @Override
    public boolean analyze(String commentary) {
        return commentary.length() <= LENGTH;
    }
}
