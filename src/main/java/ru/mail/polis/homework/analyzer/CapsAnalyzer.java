package ru.mail.polis.homework.analyzer;

public class CapsAnalyzer implements TextAnalyzer {
    @Override
    public boolean analyze(String text) {
        if (text.equals(text.toUpperCase())) {
            return !text.equals(text.toLowerCase());
        }
        return false;
    }

    @Override
    public FilterType filterType() {
        return FilterType.CAPS_TEXT;
    }
}
