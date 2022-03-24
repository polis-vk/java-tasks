package ru.mail.polis.homework.analyzer;

public class SpamAnalyzer implements TextAnalyzer {
    protected final String[] spam;

    public SpamAnalyzer(String[] spam) {
        this.spam = spam;
    }

    @Override
    public FilterType analyze(String text) {
        if (text == null) {
            return FilterType.GOOD;
        }
        for (String words : spam) {
            if (text.contains(words)) {
                return FilterType.SPAM;
            }
        }
        return FilterType.GOOD;
    }
}
