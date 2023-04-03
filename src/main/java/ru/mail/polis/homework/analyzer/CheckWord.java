package ru.mail.polis.homework.analyzer;

public interface CheckWord {
    default boolean checkWord(String text, String[] wrongWords) {
        for (String word : wrongWords) {
            if (text.contains(word)) {
                return true;
            }
        }
        return false;
    }
}
