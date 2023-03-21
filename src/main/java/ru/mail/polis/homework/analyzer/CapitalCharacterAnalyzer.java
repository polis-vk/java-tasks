package ru.mail.polis.homework.analyzer;
public class CapitalCharacterAnalyzer implements TextAnalyzer {
    @Override
    public boolean problemDetected(String text) {
        return !text.equals(text.toLowerCase());
    }
    @Override
    public FilterType getFilterType() {
        return FilterType.CAPITAL_CHAR;
    }
}