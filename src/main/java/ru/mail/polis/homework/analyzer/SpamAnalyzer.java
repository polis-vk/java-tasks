package ru.mail.polis.homework.analyzer;

public class SpamAnalyzer implements TextAnalyzer{

    private final String[] spam;

    SpamAnalyzer(String[] spam) {
        this.spam = spam;
    }

    @Override
    public boolean check(String text) {
        for (String symbol : spam) {
            if (text.contains(symbol)) {
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