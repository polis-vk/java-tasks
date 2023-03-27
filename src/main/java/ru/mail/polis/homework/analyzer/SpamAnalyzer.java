package ru.mail.polis.homework.analyzer;

public class SpamAnalyzer implements TextAnalyzer {
    private final FilterType type = FilterType.SPAM;
    private final String[] spamArray;

    SpamAnalyzer(String[] spam) {
        this.spamArray = spam;
    }

    @Override
    public boolean filterWorked(String str) {
        for (String subStr : spamArray) {
            if (str.contains(subStr)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public FilterType getType() {
        return type;
    }


}
