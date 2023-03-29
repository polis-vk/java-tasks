package ru.mail.polis.homework.analyzer;

public class TooLongAnalyzer implements TextAnalyzer {

    protected final long maxLength;

    public TooLongAnalyzer(long length) {
        this.maxLength = length;
    }

    public boolean analyzer(String text) {
        if (text == null || text.isEmpty()) {
            return false;
        }
        return text.length() > maxLength;
    }

    public FilterType getType() {
        return FilterType.TOO_LONG;
    }

}
