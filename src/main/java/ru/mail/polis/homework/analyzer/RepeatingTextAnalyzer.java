package ru.mail.polis.homework.analyzer;

import java.util.*;

public class RepeatingTextAnalyzer implements TextAnalyzer {
    private final int MAX_REPEATINGS;
    private final TreeMap<String, Integer> WORDS_SORTED;
    private FilterType filterType;
    private int wordCount;

    RepeatingTextAnalyzer(int maxRepeatings) {
        WORDS_SORTED = new TreeMap<>();
        MAX_REPEATINGS = maxRepeatings;
    }

    public FilterType analyze(String text) {
        WORDS_SORTED.clear();
        filterType = FilterType.GOOD;
        return toStringList(text);
    }

    @Override
    public int getPriority() {
        return FilterType.REPEATING_TEXT.getPriority();
    }

    public FilterType toStringList(String text) {
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
        if (WORDS_SORTED.containsKey(word)) {
            wordCount = WORDS_SORTED.get(word);
            WORDS_SORTED.replace(word, wordCount, wordCount + 1);
        } else {
            WORDS_SORTED.put(word, 1);
        }

        wordCount = WORDS_SORTED.get(word);
        if (wordCount > MAX_REPEATINGS) {
            return FilterType.REPEATING_TEXT;
        }
        return FilterType.GOOD;
    }
}
