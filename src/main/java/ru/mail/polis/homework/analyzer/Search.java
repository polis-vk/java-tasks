package ru.mail.polis.homework.analyzer;

public class Search {
    public static boolean search(String commentary, String[] words) {
        for (String word : words) {
            if (commentary.contains(word)) {
                return false;
            }
        }
        return true;
    }
}
