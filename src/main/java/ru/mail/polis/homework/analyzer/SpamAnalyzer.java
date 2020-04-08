package ru.mail.polis.homework.analyzer;

public class SpamAnalyzer implements TextAnalyzer{
    private String[] spam;
    private Integer priority;

    public SpamAnalyzer(String[] spam) {
        this.spam = spam;
        priority = FilterType.SPAM.ordinal();
    }

    public FilterType analysis(String text) {
        for (String word: spam) {
            if (text.contains(word)) {
                return FilterType.SPAM;
            }
        }
        return  FilterType.GOOD;
    }

    public Integer getPriority() {
        return priority;
    }
}
