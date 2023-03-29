package ru.mail.polis.homework.analyzer;

public class NegativeTextAnalyzer implements TextAnalyzer {

    @Override
    public boolean analyzeText(String text) {
        if (text == null || text.isEmpty()) {
            return false;
        }
        String[] banWords = new String[]{"=(", ":(", ":|"};
        for (String banWord : banWords) {
            if (text.contains(banWord)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public FilterType getType() {
        return FilterType.NEGATIVE_TEXT;
    }
}
