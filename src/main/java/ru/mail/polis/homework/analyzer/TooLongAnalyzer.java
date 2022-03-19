package ru.mail.polis.homework.analyzer;

public class TooLongAnalyzer implements TextAnalyzer {
    private final long LENGTH;

    public TooLongAnalyzer(long maxLength) {
        this.LENGTH = maxLength;
    }


    @Override
    public boolean analyze(String text) {
        return text.length() >= LENGTH;
    }


    public FilterType getType() {
        return FilterType.TOO_LONG;
    }

}
