package ru.mail.polis.homework.analyzer;

public class TooLongAnalyzer implements TextAnalyzer {
    private final FilterType filterType;
    private final long permissibleLength;

    public TooLongAnalyzer(long permissibleLength) {
        this.permissibleLength = permissibleLength;
        this.filterType = FilterType.TOO_LONG;
    }

    @Override
    public FilterType getType() {
        return this.filterType;
    }

    @Override
    public FilterType makeAnalysis(String text) {
        if (text.length() > this.permissibleLength) {
            return this.filterType;
        }
        return FilterType.GOOD;
    }
}
