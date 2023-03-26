package ru.mail.polis.homework.analyzer;

public class NegativeTextAnalyzer implements TextAnalyzer {
    @Override
    public boolean filterSuccess(String text) {
        return text.contains("=(") || text.contains(":(") || text.contains(":|");
    }

    @Override
    public FilterType filterType() {
        return FilterType.NEGATIVE_TEXT;
    }
}
