package ru.mail.polis.homework.analyzer;

public class SpamOrNegativeTextAnalyzer implements TextAnalyzer {
    private static final String[] NEGATIVE_WORDS = new String[]{"=(", ":(", ":|"};
    private final String[] wordList;
    private boolean isNegative;

    public SpamOrNegativeTextAnalyzer() {
        wordList = NEGATIVE_WORDS;
        isNegative = true;
    }

    public SpamOrNegativeTextAnalyzer(String[] spam) {
        this.wordList = spam;
    }

    @Override
    public FilterType analyze(String text) {
        for (String word : wordList) {
            if (text.contains(word)) {
                if (isNegative) {
                    return FilterType.NEGATIVE_TEXT;
                }
                return FilterType.SPAM;
            }
        }
        return FilterType.GOOD;
    }
}
