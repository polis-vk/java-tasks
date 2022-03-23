package ru.mail.polis.homework.analyzer;

public class SpamFilter implements TextAnalyzer {

    private final String[] spam;

    public SpamFilter(String[] spam) {
        this.spam = spam;
    }

    @Override
    public FilterType analyzer(String str) {
        for (String temp : spam) {
            if (str.contains(temp)) {
                return FilterType.SPAM;
            }
        }

        return FilterType.GOOD;
    }
}
