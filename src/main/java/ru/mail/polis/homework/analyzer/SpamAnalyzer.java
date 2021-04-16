package ru.mail.polis.homework.analyzer;

public class SpamAnalyzer implements TextAnalyzer {
    private String[] spam;
    private byte priority = 1;

    public SpamAnalyzer(String[] spam) {
        this.spam = spam;
    }

    public boolean check(String str) {
        for (int i = 0; i < spam.length; i++) {
            if (str.contains(spam[i])) {
                return true;
            }
        }
        return false;
    }

    public FilterType filter() {
        return FilterType.SPAM;
    }

    public byte priority() {
        return priority;
    }
}