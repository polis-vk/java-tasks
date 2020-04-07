package ru.mail.polis.homework.analyzer;

public class TooLongAnalyzer implements TextAnalyzer {
    
    private final long maxLength;
    
    TooLongAnalyzer(long maxLength) {
        this.maxLength = maxLength;
    }
    
    
    @Override
    public FilterType getFilterType() {
        return FilterType.TOO_LONG;
    }
    
    @Override
    public boolean isTriggered(String text) {
        if (text == null || text.isEmpty()) {
            return false;
        }
        return text.length() > maxLength;
    }
}
