package ru.mail.polis.homework.analyzer;

public class SpamAnalyzer implements TextAnalyzer {
    private final String[] banWords;

    public SpamAnalyzer(String[] spam) {
        banWords = spam;
    }

    @Override
    public boolean analyze(String comment) {
        for (String word : banWords) {
            if (comment.contains(word)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public FilterType getFilterType() {
        return FilterType.SPAM;
    }
}
