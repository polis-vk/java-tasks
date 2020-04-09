package ru.mail.polis.homework.analyzer;

public class IsTooLong implements TextAnalyzer {

    private long textLength;
    public IsTooLong(long maxLength) {
        textLength = maxLength;
    }

    @Override
    public FilterType analyze(String arg) {
        if (arg.length() > textLength) {
            return FilterType.TOO_LONG;
        }
        return FilterType.GOOD;
    }

    @Override
    public FilterType getFilterAnswer() {
        return FilterType.TOO_LONG;
    }
}
