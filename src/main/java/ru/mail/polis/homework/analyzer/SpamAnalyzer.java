package ru.mail.polis.homework.analyzer;


public class SpamAnalyzer implements TextAnalyzer {
    int priority = 1;
    String[] badWords;

    SpamAnalyzer(String[] badWords) {
        this.badWords = badWords.clone();
    }


    @Override
    public int getPriority() {
        return priority;
    }

    @Override
    public boolean analyze(String commentary) {
        for (String word : badWords) {
            if (commentary.contains(word)) {
                return false;
            }
        }
        return true;
    }
}
