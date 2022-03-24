package ru.mail.polis.homework.analyzer;

public class LongSpaceAnalyzer implements TextAnalyzer {

    @Override
    public boolean check(String text) {
        return text.contains("  ");
    }

    @Override
    public FilterType getFilterType() {
        return FilterType.LONG_SPACE;
    }
}
