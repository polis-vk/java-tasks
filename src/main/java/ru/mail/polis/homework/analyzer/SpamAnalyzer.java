package ru.mail.polis.homework.analyzer;

public class SpamAnalyzer implements TextAnalyzer {

    private final String[] badWords;

    SpamAnalyzer(String[] spam) {
        badWords = spam;
    }

    @Override
    public boolean filterOfType(String text) {
        for (String word : badWords) {
            if (text.contains(word)) {
                return true;
            }
        }
        return false;
    }

    public FilterType getType() {
        return FilterType.SPAM;
    }
}
