package ru.mail.polis.homework.analyzer;

public class NegativeTextAnalyzer implements TextAnalyzer {
    private final String[] BANWORDS = {"=(", ":(", ":|"};

    @Override
    public FilterType analyze(String comment) {
        for (String word: BANWORDS) {
            if (comment.contains(word)) {
                return FilterType.NEGATIVE_TEXT;
            }
        }
        return FilterType.GOOD;
    }
}
