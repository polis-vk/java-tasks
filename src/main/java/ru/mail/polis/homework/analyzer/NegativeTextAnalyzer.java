package ru.mail.polis.homework.analyzer;

public class NegativeTextAnalyzer implements TextAnalyzer {
    private final String[] NEGATIVE_WORDS = {"=(", ":(", ":|"};

    @Override
    public FilterType getTypeFilter() {
        return FilterType.NEGATIVE_TEXT;
    }

    @Override
    public boolean isCorrect(String text) {
        return text == null || text.isEmpty() || new SpamAnalyzer(NEGATIVE_WORDS).isCorrect(text);
    }
}
