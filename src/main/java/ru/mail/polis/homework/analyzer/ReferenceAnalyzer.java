package ru.mail.polis.homework.analyzer;

public class ReferenceAnalyzer implements TextAnalyzer {
    
    private static final String REGEX = "^https?://([\\w]+(-)?[\\w]+\\.)+[\\w]+(/([\\?\\.\\_\\=\\#]?[\\w])+)*/?$";
    
    @Override
    public FilterType getFilterType() {
        return FilterType.REFERENCE;
    }
    
    @Override
    public boolean isTriggered(String text) {
        if (text == null || text.isEmpty()) {
            return false;
        }
        String[] words = text.split("(\\s)+");
        for (String word : words) {
            if (word.matches(REGEX)) {
                return true;
            }
        }
        return false;
    }
}
