package ru.mail.polis.homework.analyzer;

public class SpamAnalyzer implements TextAnalyzer {
    private final String[] spamStrings;
    private final FilterType type = FilterType.SPAM;

    SpamAnalyzer(String[] spamStrings) {
        this.spamStrings = spamStrings;
    }

    @Override
    public boolean resultResearch(String str) {
        for (String elem : spamStrings) {
            if (str.contains(elem)) {
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

