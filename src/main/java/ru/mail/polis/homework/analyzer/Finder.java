package ru.mail.polis.homework.analyzer;

public class Finder {
    public static boolean find(String[] array, String sentence) {
        for (String word : array) {
            if (sentence.contains(word)) {
                return true;
            }
        }
        return false;
    }
}
