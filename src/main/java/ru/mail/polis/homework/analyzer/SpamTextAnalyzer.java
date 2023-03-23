package ru.mail.polis.homework.analyzer;

public class SpamTextAnalyzer implements TextAnalyzer {
    String[] spamWords;

    public SpamTextAnalyzer(String[] spam) {
        spamWords = spam;
    }

    @Override
    public FilterType getFilterType() {
        return FilterType.SPAM;
    }

    @Override
    public boolean check(String text) {
        for (String word : spamWords) {
            if (text.contains(word)) {
                return true;
            }
        }
        return false;
    }


}
