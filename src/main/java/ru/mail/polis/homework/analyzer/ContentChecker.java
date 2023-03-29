package ru.mail.polis.homework.analyzer;

public class ContentChecker {
    protected static boolean textContainWord(String text, String[] src) {
        for (String word : src) {
            if (text.contains(word)) {
                return true;
            }
        }
        return false;
    }
}
