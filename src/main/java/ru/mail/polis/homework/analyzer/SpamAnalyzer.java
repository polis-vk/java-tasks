package ru.mail.polis.homework.analyzer;


import java.util.Arrays;

public class SpamAnalyzer implements TextAnalyzer {
    private static final int priority = 1;
    private static final FilterType filterType = FilterType.SPAM;
    private final String[] badWords;

    SpamAnalyzer(String[] badWords) {
        this.badWords = Arrays.copyOf(badWords, badWords.length);
    }


    @Override
    public int getPriority() {
        return priority;
    }

    @Override
    public FilterType getFilterType() {
        return filterType;
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
