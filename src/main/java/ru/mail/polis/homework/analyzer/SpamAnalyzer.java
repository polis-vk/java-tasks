package ru.mail.polis.homework.analyzer;

import java.util.logging.Filter;

public class SpamAnalyzer implements TextAnalyzer {

    private final String[] badWords;

    SpamAnalyzer(String[] spam) {
        badWords = spam;
    }

    @Override
    public FilterType filterOfType(String text) {
        for (String word : badWords) {
            if (text.contains(word)) {
                return priority();
            }
        }
        return FilterType.GOOD;
    }

    public FilterType priority() {
        return FilterType.SPAM;
    }
}
