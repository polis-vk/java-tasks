package ru.mail.polis.homework.analyzer;

import java.util.*;

public class RepeatingTextAnalyzer implements TextAnalyzer {
    int maxRepeatings;

    RepeatingTextAnalyzer(int maxRepeatings) {
        this.maxRepeatings = maxRepeatings;
    }

    public FilterType analyze(String text) {
        TreeMap<String, Integer> words = toStringList(text);

        for (Map.Entry<String, Integer> word : words.entrySet()) {
            if (word.getValue() > maxRepeatings) {
                return FilterType.REPEATING_TEXT;
            }
        }
        return FilterType.GOOD;
    }

    public static TreeMap<String, Integer> toStringList(String text) {
        TreeMap<String, Integer> wordsSorted = new TreeMap<>();
        String separators = " ,.\"()!:;?+=«»[]";
        int wordStartIndex = -1;

        for (int i = 0; i < text.length(); i++) {
            char currentChar = text.charAt(i);
            if (separators.indexOf(currentChar) != -1) {
                if (wordStartIndex != -1) {
                    appendMap(wordsSorted, text.substring(wordStartIndex, i));
                    wordStartIndex = -1;
                }
            } else {
                if (wordStartIndex == -1) {
                    wordStartIndex = i;
                }
                if (i == text.length() - 1) {
                    appendMap(wordsSorted, text.substring(wordStartIndex, i + 1));
                }
            }
        }
        return wordsSorted;
    }

    private static void appendMap(TreeMap<String, Integer> wordsSorted, String word) {
        if (wordsSorted.containsKey(word)) {
            int wordCount = wordsSorted.get(word);
            wordsSorted.replace(word, wordCount, wordCount + 1);
        } else {
            wordsSorted.put(word, 1);
        }
    }
}
