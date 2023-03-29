package ru.mail.polis.homework.analyzer;

import java.util.Arrays;

public class SpamTextAnalyzer implements TextAnalyzer {
    private String[] banWords;

    public SpamTextAnalyzer(String[] banWords) {
        this.banWords = Arrays.copyOf(banWords, banWords.length);
    }

    @Override
    public boolean analyzeText(String text) {
        if (text == null || text.isEmpty()) {
            return false;
        }
        for (String banWord : this.banWords) {
            if (text.contains(banWord)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public FilterType getType() {
        return FilterType.SPAM;
    }
}
