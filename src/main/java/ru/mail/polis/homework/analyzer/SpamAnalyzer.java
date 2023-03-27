package ru.mail.polis.homework.analyzer;


import java.util.Arrays;

import static ru.mail.polis.homework.analyzer.Search.search;

public class SpamAnalyzer implements TextAnalyzer {
    private static final int PRIORITY = 1;
    private final String[] BAD_WORDS;

    public SpamAnalyzer(String[] badWords) {
        this.BAD_WORDS = Arrays.copyOf(badWords, badWords.length);
    }


    @Override
    public int getPriority() {
        return PRIORITY;
    }

    @Override
    public FilterType getFilterType() {
        return FilterType.SPAM;
    }

    @Override
    public boolean analyze(String commentary) {
        return search(commentary, BAD_WORDS);
    }
}
