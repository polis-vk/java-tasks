package ru.mail.polis.homework.analyzer;

public class CapsLockTextAnalyzer implements TextAnalyzer {

    @Override
    public boolean analyzeText(String text) {
        return text.equals(text.toUpperCase());
    }

    @Override
    public FilterType getFilterType() {
        return FilterType.CAPSLOCK;
    }
}
