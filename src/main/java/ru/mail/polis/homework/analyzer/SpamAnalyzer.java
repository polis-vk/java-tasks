package ru.mail.polis.homework.analyzer;

public class SpamAnalyzer implements TextAnalyzer {

    private final String[] spamList;

    public SpamAnalyzer(String[] spam) {
        this.spamList = spam;
    }

    @Override
    public FilterType getType() {
        return FilterType.SPAM;
    }

    @Override
    public boolean check(String str) {
        for (String spamWord: spamList) {
            if (str.contains(spamWord)) {
                return true;
            }
        }
        return false;
    }
}
