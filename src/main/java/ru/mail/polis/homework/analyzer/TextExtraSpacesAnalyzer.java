package ru.mail.polis.homework.analyzer;

public class TextExtraSpacesAnalyzer implements TextAnalyzer {

    @Override
    public boolean analyze(String text) {
        return !text.equals(text.replaceAll("[\\s]{2,}", " ").trim());
    }

    @Override
    public FilterType getType() {
        return FilterType.EXTRA_SPACE;
    }
}
