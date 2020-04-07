package ru.mail.polis.homework.analyzer;

public class SpamAnalyzer implements TextAnalyzer {
    
    private final String[] spamWords;
    
    SpamAnalyzer(String[] spam) {
        this.spamWords = spam == null ? new String[0] : spam.clone();
    }
    
    @Override
    public FilterType getFilterType() {
        return FilterType.SPAM;
    }
    
    @Override
    public boolean isTriggered(String text) {
        if (text == null || text.isEmpty()) {
            return false;
        }
        for (String spamWord : spamWords) {
            if (text.contains(spamWord)) {
                return true;
            }
        }
        return false;
    }
}
