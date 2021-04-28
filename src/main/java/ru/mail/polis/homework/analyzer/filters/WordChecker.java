package ru.mail.polis.homework.analyzer.filters;

import ru.mail.polis.homework.analyzer.TextAnalyzer;

public abstract class WordChecker implements TextAnalyzer {

    private final String[] words;

    public WordChecker(String[] words) {
        this.words = words;
    }

    public boolean check(String text) {

        if (text == null) {
            return false;
        }

        for (String word : words) {
            if (text.contains(word)) {
                return true;
            }
        }

        return false;
    }
}
