package ru.mail.polis.homework.analyzer;

public class SpamAnalyzer implements TextAnalyzer {
     private String[] spam;

    public SpamAnalyzer(String[] spam) {
        this.spam = spam;
    }

    public boolean isTrue(String text) {
        for (String spamWord : spam) {
            if (text.contains(spamWord)) {
                return true;
            }
        }
        return false;
    }

    public FilterType getFilterType() {
        return FilterType.SPAM;
    }
}
