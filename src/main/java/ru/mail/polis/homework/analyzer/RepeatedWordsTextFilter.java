package ru.mail.polis.homework.analyzer;

import java.util.HashMap;
import java.util.Map;

public class RepeatedWordsTextFilter implements TextAnalyzer {

    private int maxRepeatCount;

    public RepeatedWordsTextFilter(int maxRepeatCount) {
        this.maxRepeatCount = maxRepeatCount;
    }

    @Override
    public boolean analyzeText(String text) {
        if (text == null || text.isEmpty()) {
            return false;
        }
        String[] words = text.split("\\s+");

        Map<String, Integer> wordCounts = new HashMap<>();

        // Считаем количество вхождений каждого слова
        for (String word : words) {
            wordCounts.put(word, wordCounts.getOrDefault(word, 0) + 1);
        }

        for (String word : words) {
            if (wordCounts.get(word) > maxRepeatCount) {
                return false;
            }
        }
        return true;
    }

    @Override
    public FilterType getType() {
        return FilterType.REPEATED_TEXT;
    }
}
