package ru.mail.polis.homework.analyzer;

public class NegativeTextAnalyzer implements TextAnalyzer {

    @Override
    public int getPriority() {
        return 3;
    }

    @Override
    public FilterType getFilterType(String str) {
        if (str.contains("=(") || str.contains(":(") || str.contains(":|")) {
            return FilterType.NEGATIVE_TEXT;
        }
        return FilterType.GOOD;
    }

}
