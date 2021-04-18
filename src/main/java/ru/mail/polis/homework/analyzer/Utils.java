package ru.mail.polis.homework.analyzer;

public class Utils {
    public static boolean contains(String string, String[] words) {
        for (String word : words) {
            if (string.contains(word)) {
                return true;
            }
        }
        return false;
    }
}
