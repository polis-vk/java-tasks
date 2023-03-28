package ru.mail.polis.homework.analyzer;


import java.util.Arrays;

public class SpamAnalyzer implements TextAnalyzer {
    private final String[] badWords;

    public SpamAnalyzer(String[] badWords) {
        this.badWords = Arrays.copyOf(badWords, badWords.length);
    }

    @Override
    public FilterType getFilterType() {
        return FilterType.SPAM;
    }

    @Override
    public boolean analyze(String commentary) {
        for (String word : badWords) {
            if (commentary.contains(word)) {
                return false;
            }
        }
        return true;
    }
}
