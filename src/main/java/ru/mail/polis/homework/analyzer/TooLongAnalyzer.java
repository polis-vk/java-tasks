package ru.mail.polis.homework.analyzer;

public class TooLongAnalyzer implements TextAnalyzer {

    private final long maxLength;

    public TooLongAnalyzer(long maxLength) {
        this.maxLength = maxLength;
    }

    public long getMaxLength() {
        return maxLength;
    }

    @Override
    public boolean checkCorrection(String text) {
        if (text == null) {
            return true;
        }
        return text.length() <= maxLength;
    }

    @Override
    public FilterType getFilterType() {
        return FilterType.TOO_LONG;
    }
}
