package ru.mail.polis.homework.analyzer;

public class SpamTextAnalyzer implements TextAnalyzer {

    private String[] spam;

    SpamTextAnalyzer(String[] spam) {
        this.spam = spam;
    }

    @Override
    public FilterType analyze(String str) {
        if (str == null) {
            return FilterType.GOOD;
        }
        for (String spamWord : spam) {
            if (str.contains(spamWord)) {
                return getFilterType();
            }
        }
        return FilterType.GOOD;
    }

    @Override
    public FilterType getFilterType() {
        return FilterType.SPAM;
    }
    /* Код для второй версии компаратора */
    private int priority = 1;

    public int getPriority() {
        return priority;
    }
    /*----------------------------*/
}
