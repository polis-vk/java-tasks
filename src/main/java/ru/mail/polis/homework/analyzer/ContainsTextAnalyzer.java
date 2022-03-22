package ru.mail.polis.homework.analyzer;

public abstract class ContainsTextAnalyzer implements TextAnalyzer {
    private final String[] dictionary;

    public ContainsTextAnalyzer(String[] dictionary) {
        this.dictionary = dictionary;
    }

    @Override
    public boolean analyze(String text) {
        for (String word : dictionary) {
            if (text.contains(word)) {
                return true;
            }
        }
        return false;
    }
}
