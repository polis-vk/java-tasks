package ru.mail.polis.homework.analyzer.custom;

import ru.mail.polis.homework.analyzer.TextAnalyzer;

public abstract class WrongWordsAnalyzer implements TextAnalyzer {
    private final String[] wrongWords;

    public WrongWordsAnalyzer(String[] wrongWords) {
        this.wrongWords = wrongWords.clone();
    }

    @Override
    public boolean haveProblem (String text) {
        for (String wrongWord : wrongWords) {
            if (text.contains(wrongWord)) {
                return true;
            }
        }
        return false;
    }
}
