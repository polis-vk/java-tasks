package ru.mail.polis.homework.analyzer;

public class SpamAnalyzer implements TextAnalyzer {
    private String[] badWords;

    public SpamAnalyzer(String[] badWords) {
        this.badWords = badWords;
    }

    public FilterType check(String txt) {
        if (TextAnalyzer.find(badWords, txt)) {
            return FilterType.SPAM;
        }
        return null;
    }
}
