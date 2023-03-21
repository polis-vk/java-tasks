package ru.mail.polis.homework.analyzer;


public class MatchSearcher {
    public boolean textContainsSthFromArray(String text, String[] array) {
        for (String s : array) {
            if (text.contains(s)) {
                return true;
            }
        }
        return false;
    }
}