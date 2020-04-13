package ru.mail.polis.homework.analyzer;

public class IsTooLong implements TextAnalyzer {

    private final long TEXT_LENGTH;
    public IsTooLong(long maxLength) {
        TEXT_LENGTH = maxLength;
    }

    @Override
    public FilterType analyze(String arg) {
        if (arg.length() > TEXT_LENGTH) {
            return FilterType.TOO_LONG;
        }
        return FilterType.GOOD;
    }

    @Override
    public FilterType getFilterAnswer() {
        return FilterType.TOO_LONG;
    }
}
