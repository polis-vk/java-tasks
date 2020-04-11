package ru.mail.polis.homework.analyzer;

public class ReferenceAnalyzer implements TextAnalyzer {
    
    @Override
    public FilterType getFilterType() {
        return FilterType.REFERENCE;
    }
    
    @Override
    public boolean isTriggered(String text) {
        if (text == null || text.isEmpty()) {
            return false;
        }
        String regex = "^https?://([\\w]+(-)?[\\w]+\\.)+[\\w]+(/([\\?\\.\\_\\=\\#]?[\\w])+)*/?$";
        String[] words = text.split("(\\s)+");
        for (String word : words) {
            if (word.matches(regex)) {
                return true;
            }
        }
        return false;
    }
}
