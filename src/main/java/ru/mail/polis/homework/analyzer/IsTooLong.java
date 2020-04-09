package ru.mail.polis.homework.analyzer;

public class IsTooLong implements TextAnalyzer {

    private int priority = 1;
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
    public int getPriority() {
        return priority;
    }

    @Override
    public void setPriority(int newPriority) {
        this.priority = newPriority;
    }
}
