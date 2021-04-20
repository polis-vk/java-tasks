package ru.mail.polis.homework.analyzer.filters;

import ru.mail.polis.homework.analyzer.FilterType;

public class SpamAnalyzer extends Filter {

    private String[] spamWords;

    @Override
    public boolean check(String text) {

        if (text == null) {
            return false;
        }

        for (String word : spamWords) {
            if (text.contains(word)) {
                return true;
            }
        }

        return false;
    }

    @Override
    public FilterType getFilterType() {
        return FilterType.SPAM;
    }

    public SpamAnalyzer(String[] words) {
        spamWords = words;
        order = 1;
    }
}
