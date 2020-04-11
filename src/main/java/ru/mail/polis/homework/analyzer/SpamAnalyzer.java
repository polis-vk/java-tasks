package ru.mail.polis.homework.analyzer;

public class SpamAnalyzer extends BadWordsAnalyzer {
    
    private final String[] spamWords;
    
    public SpamAnalyzer(String[] spam) {
        this.spamWords = spam == null ? new String[0] : spam.clone();
    }
    
    @Override
    public FilterType getFilterType() {
        return FilterType.SPAM;
    }
    
    @Override
    protected String[] getBadWords() {
        return spamWords;
    }
}
