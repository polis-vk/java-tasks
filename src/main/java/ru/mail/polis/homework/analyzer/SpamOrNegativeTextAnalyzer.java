package ru.mail.polis.homework.analyzer;

public class SpamOrNegativeTextAnalyzer implements TextAnalyzer {
    private static final String[] NEGATIVE_WORDS = new String[]{"=(", ":(", ":|"};
    private final String[] wordList;
    private final FilterType filterType;

    public SpamOrNegativeTextAnalyzer() {
        wordList = NEGATIVE_WORDS;
        filterType = FilterType.NEGATIVE_TEXT;
    }

    public SpamOrNegativeTextAnalyzer(String[] spam) {
        wordList = spam;
        filterType = FilterType.SPAM;
    }

    @Override
    public FilterType analyze(String text) {
        for (String word : wordList) {
            if (text.contains(word)) {
                return filterType;
            }
        }
        return FilterType.GOOD;
    }

    @Override
    public int getPriority() {
        return filterType.getPriority();
    }
}
