package ru.mail.polis.homework.analyzer;

public class CapsAnalyzer implements TextAnalyzer {
    @Override
    public boolean filterSuccess(String text) {
        return text.equals(text.toUpperCase());
    }

    @Override
    public FilterType filterType() {
        return FilterType.CAPS_TEXT;
    }
}
