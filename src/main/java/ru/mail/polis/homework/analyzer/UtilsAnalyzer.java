package ru.mail.polis.homework.analyzer;

public class UtilsAnalyzer {
    public static boolean isContainsMorphemes(String text, String[] morphemes) {
        for (String s : morphemes) {
            if (text.contains(s)) {
                return true;
            }
        }
        return false;
    }
}
