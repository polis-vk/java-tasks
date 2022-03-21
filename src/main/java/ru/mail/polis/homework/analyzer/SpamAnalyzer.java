package ru.mail.polis.homework.analyzer;

public class SpamAnalyzer implements TextAnalyzer{
    private String[] spam;
    public SpamAnalyzer(String[] spam) {
        this.spam = spam;
    }
    @Override
    public FilterType analyze(String text) {
        if (text == null) {
            return FilterType.GOOD;
        }
        for (String curSpam:spam) {
            if (text.contains(curSpam)) {
                return FilterType.SPAM;
            }
        }
        return FilterType.GOOD;
    }
}
