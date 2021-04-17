package ru.mail.polis.homework.analyzer;

public class NegativeTextAnalyzer implements TextAnalyzer {
    private static final String[] NEGATIVE_WORDS = {"=(", ":(", ":|"};
    private static final SpamAnalyzer ANALYZER = new SpamAnalyzer(NEGATIVE_WORDS);

    @Override
    public FilterType getTypeFilter() {
        return FilterType.NEGATIVE_TEXT;
    }

    @Override
    public boolean isCorrect(String text) {
        return text == null || text.isEmpty() || ANALYZER.isCorrect(text);
    }
}
