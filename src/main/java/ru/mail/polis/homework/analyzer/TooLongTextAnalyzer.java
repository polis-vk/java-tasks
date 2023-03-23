package ru.mail.polis.homework.analyzer;

public class TooLongTextAnalyzer implements TextAnalyzer {
    long length;

    public TooLongTextAnalyzer(long length) {
        this.length = length;
    }

    @Override
    public FilterType getType() {

        return FilterType.TOO_LONG;
    }

    @Override
    public boolean analyzeText(String text) {
        if (text == null || text.isEmpty()) {
            return false;
        }
        return text.length() >= this.length;
    }
}
