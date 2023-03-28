package ru.mail.polis.homework.analyzer;

public class SpamAnalyzer implements TextAnalyzer {
    private final String[] arrayOfSpam;

    SpamAnalyzer(String[] spam) {
        this.arrayOfSpam = spam;
    }

    @Override
    public boolean isAnalyze(String str) {
        for (String spam : arrayOfSpam) {
            if (str.contains(spam)) {
                return false;
            }
        }
        return true;
    }

    @Override
    public FilterType getType() {
        return FilterType.SPAM;
    }
}
