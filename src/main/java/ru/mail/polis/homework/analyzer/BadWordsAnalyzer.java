package ru.mail.polis.homework.analyzer;

public abstract class BadWordsAnalyzer implements TextAnalyzer {
    
    protected abstract String[] getBadWords();
    
    @Override
    public boolean isTriggered(String text) {
        if (text == null || text.isEmpty()) {
            return false;
        }
        for (String spamWord : getBadWords()) {
            if (text.contains(spamWord)) {
                return true;
            }
        }
        return false;
    }
}
