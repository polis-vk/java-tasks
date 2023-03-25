package ru.mail.polis.homework.analyzer;

public class SpamTextAnalyzer implements TextAnalyzer {

    private String[] spams = {};

    SpamTextAnalyzer(String[] spams) {
        this.spams = spams;
    }

    @Override
    public FilterType analyze(String str) {
        for (String spams : this.spams) {
            if (str.contains(spams)) {
                return FilterType.SPAM;
            }
        }
        return FilterType.GOOD;
    }
}
