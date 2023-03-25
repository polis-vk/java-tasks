package ru.mail.polis.homework.analyzer;

public class TooLongAnalyzer implements TextAnalyzer {
    long maxLength;

    TooLongAnalyzer(long maxLength) {
        this.maxLength = maxLength;
    }

    @Override
    public FilterType analyzeText(String text) {
        FilterType resultType = FilterType.GOOD;
        if (text == null) {
            return resultType;
        }
        if (text.length() > maxLength) {
            resultType = FilterType.TOO_LONG;
        }
        return resultType;
    }

    @Override
    public FilterType getFilterType() {
        return FilterType.TOO_LONG;
    }
}
