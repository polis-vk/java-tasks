package ru.mail.polis.homework.analyzer;

public class SpamTextAnalyzer implements TextAnalyzer {
    private final String[] spam;

    SpamTextAnalyzer(String[] spam) {
        this.spam = spam;
    }

    @Override
    public FilterType FilterValue(String text) {
        for (int i = 0; i < spam.length; i++) {
            if (text.contains(spam[i])) {
                return FilterType.SPAM;
            }
        }
        return FilterType.GOOD;
    }
}
