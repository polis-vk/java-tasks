package ru.mail.polis.homework.analyzer;

public class TooLongTextAnalyzer implements TextAnalyzer {
    private long length;

    public TooLongTextAnalyzer(long length) {
        this.length = length;
    }

    @Override
    public FilterType getType() {

        return FilterType.TOO_LONG;
    }

    @Override
    public boolean analyzeText(String text) {
        return text.length() > this.length;
    }
}
