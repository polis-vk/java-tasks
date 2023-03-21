package ru.mail.polis.homework.analyzer;

public class SpamAnalyzer implements TextAnalyzer {
    private final String[] spam;

    public SpamAnalyzer(String[] spam) {
        this.spam = spam;
    }

    @Override
    public int getPriority() {
        return 1;
    }

    @Override
    public FilterType getFilterType(String str) {
        for (String s : spam) {
            if (str.contains(s)) {
                return FilterType.SPAM;
            }
        }
        return FilterType.GOOD;
    }
}
