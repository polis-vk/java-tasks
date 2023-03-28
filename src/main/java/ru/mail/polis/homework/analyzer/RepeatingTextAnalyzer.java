package ru.mail.polis.homework.analyzer;

import java.util.*;

public class RepeatingTextAnalyzer implements TextAnalyzer {
    private final int maxRepeatings;
    private final TreeMap<String, Integer> wordsSorted;
    private FilterType filterType;

    RepeatingTextAnalyzer(int maxRepeatings) {
        wordsSorted = new TreeMap<>();
        this.maxRepeatings = maxRepeatings;
    }

    public FilterType analyze(String text) {
        wordsSorted.clear();
        filterType = FilterType.GOOD;
        return checkForRepeatings(text);
    }

    public FilterType checkForRepeatings(String text) {
        for (String word : text.split("[ ,.\"!:;?+=«»\\]\\[]")) {
            if (appendMap(word) == FilterType.REPEATING_TEXT) {
                return FilterType.REPEATING_TEXT;
            }
        }
        return filterType;
    }

    private FilterType appendMap(String word) {
        if (word.equals("")) {
            return FilterType.GOOD;
        }
        int wordCount;
        if (wordsSorted.containsKey(word)) {
            wordCount = wordsSorted.get(word);
            wordsSorted.replace(word, wordCount, wordCount + 1);
        } else {
            wordsSorted.put(word, 1);
        }

        wordCount = wordsSorted.get(word);
        if (wordCount > maxRepeatings) {
            return FilterType.REPEATING_TEXT;
        }
        return FilterType.GOOD;
    }
}
