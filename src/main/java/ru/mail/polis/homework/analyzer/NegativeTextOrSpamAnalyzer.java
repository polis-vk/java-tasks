package ru.mail.polis.homework.analyzer;

public class NegativeTextOrSpamAnalyzer implements TextAnalyzer {

    private final String[] filterWords;

    public NegativeTextOrSpamAnalyzer(String[] words) {
        filterWords = words;
    }

    @Override
    public FilterType getFilterType() {
        return FilterType.GOOD;
    }

    @Override
    public boolean doAnalyze(String text) {
        for (String word : filterWords) {
            if (text.contains(word)) {
                return true;
            }
        }
        return false;
    }
}
