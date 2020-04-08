package ru.mail.polis.homework.analyzer;

public class SpamAnalyzer implements TextAnalyzer {

    private String[] spam;

    SpamAnalyzer(String[] spam) {
        this.spam = spam;
    }

    public FilterType analyze(String text) {
        if (text == null || text == "") {
            return FilterType.GOOD;
        }
        for (int i = 0; i < spam.length; i++) {
            if (text.contains(spam[i])) {
                return FilterType.SPAM;
            }
        }
        return FilterType.GOOD;
    }
}
