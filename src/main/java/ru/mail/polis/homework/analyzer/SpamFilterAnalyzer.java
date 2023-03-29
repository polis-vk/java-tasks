package ru.mail.polis.homework.analyzer;

public class SpamFilterAnalyzer implements TextAnalyzer {
    private final String[] spam;

    SpamFilterAnalyzer(String[] spam) {
        this.spam = spam;
    }

    @Override
    public FilterType getType() {
        return FilterType.SPAM;
    }

    @Override
    public boolean isFilterFailed(String text) {
        return Finder.find(spam, text);
    }

}
