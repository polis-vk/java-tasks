package ru.mail.polis.homework.analyzer;

import java.util.*;

public class RepeatingTextAnalyzer implements TextAnalyzer {
    private final int MAX_REPEATINGS;
    private TreeMap<String, Integer> wordsSorted;
    private FilterType filterType;

    RepeatingTextAnalyzer(int maxRepeatings) {
        wordsSorted = new TreeMap<>();
        MAX_REPEATINGS = maxRepeatings;
    }

    public FilterType analyze(String text) {
        wordsSorted.clear();
        filterType = FilterType.GOOD;
        return toStringList(text);
    }

    public FilterType toStringList(String text) {
        for (String word: text.split("[ ,.\"!:;?+=«»\\]\\[]")) {
            if (appendMap(word) == FilterType.REPEATING_TEXT) {
                return FilterType.REPEATING_TEXT;
            }
        }
        return filterType;
    }

    private FilterType appendMap(String word) {
        if(word.equals("")){
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
        if(wordCount > MAX_REPEATINGS) {
            return FilterType.REPEATING_TEXT;
        }
        return FilterType.GOOD;
    }
}
