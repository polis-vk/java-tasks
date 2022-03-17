package ru.mail.polis.homework.analyzer;

public class SpamAnalyzer implements TextAnalyzer {

    private final String[] badWords;

    SpamAnalyzer(String[] spam) {
        badWords = spam;
    }

    @Override
    public FilterType typeOfType(String text) {
        for (String word : badWords) {
            if (text.contains(word)) {
                return FilterType.SPAM;
            }
        }
        return FilterType.GOOD;
    }

    @Override
    public int priority() {
        return FilterType.SPAM.ordinal();
    }
}
